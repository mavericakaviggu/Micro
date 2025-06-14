package com.project.authService.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.project.authService.io.ProfileRequest;
import com.project.authService.io.ProfileResponse;
import com.project.authService.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import com.project.authService.service.EmailService;


@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ProfileService profileService;
    private final EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> register(@Valid @RequestBody ProfileRequest request) {
        // Validate and pass the incoming request to the service layer
        ProfileResponse response = profileService.createProfile(request);
        // send welcone email
        try{
            //below line to be used when actual email needs to be sent
            //emailService.sendWelcomeEmail(response.getEmail(), response.getName());
            //below line if for mock email
            System.out.printf("Mock Email sent to %s. Welcome %s!%n", response.getEmail(), name);
        }catch (Exception e) {
            System.err.println("Email sending failed: " + e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email) {
        // Fetch the profile by ID
        return profileService.getProfile(email);
    }



    // @GetMapping("/test")
    // @ResponseStatus(HttpStatus.OK)
    // public String test() {
    //     return "Auth is working";
    // }

}
