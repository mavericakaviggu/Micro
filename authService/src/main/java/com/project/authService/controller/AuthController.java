package com.project.authService.controller;

import com.project.authService.io.AuthResponse;
import com.project.authService.service.ProfileService;
import com.project.authService.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.util.HashMap;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.authService.io.AuthRequest;
import com.project.authService.service.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import com.project.authService.entity.UserEntity;
import com.project.authService.repository.UserRepository;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.server.ResponseStatusException;

// This controller handles user authentication (login) and JWT token generation.
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final AppUserDetailsService appUserDetailsService;
    private final ProfileService profileService;

    @PostMapping("/login") //POST /login endpoint to authenticate a user and return a JWT token in an HTTP-only cookie.
    public ResponseEntity<?> login(@RequestBody AuthRequest request){
        try{
            authenticate(request.getEmail(),request.getPassword()); /// Perform Spring Security authentication
            final UserDetails userDetails = appUserDetailsService.loadUserByUsername(request.getEmail());
            final String jwtToken = jwtUtil.generateToken(userDetails);
            ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                    .httpOnly(true) //// Create HTTP-only cookie to store JWT securely
                    .secure(false)
                    .path("/")
                    .maxAge(Duration.ofDays(1))
                    .sameSite("Strict")// Set cookie expiration to 1 day
                    .build();
            return ResponseEntity.ok() // Return response with cookie and a body
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(new AuthResponse(request.getEmail(), jwtToken));
        }
        // If credentials are wrong
        catch(BadCredentialsException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error",true);
            error.put("message", "Email or password is incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        // If account is disabled
        catch(DisabledException e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error",true);
            error.put("message", "Account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
        // Generic authentication failure
        catch(Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error",true);
            error.put("message", "Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }

    private void authenticate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @GetMapping("/is-authenticated") 
    public ResponseEntity<Boolean> isAuthenticated(@CurrentSecurityContext(expression = "authentication?.name") String email) { //This injects the currently authenticated user's email (username) directly into the method parameter.
        return ResponseEntity.ok(email != null);
    }

    @PostMapping("/send-reset-otp") //POST call "http://localhost:8084/api/v1.0/send-reset-otp?email=veenanayak@gmail.com"
    public void sendResetOtp(@RequestParam String email) {
        try{
            profileService.sendResetOtp(email);
        }catch(Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to send reset OTP email: " + ex.getMessage());
        }
    }
    

}
