package com.project.authService.util;


import java.util.HashMap;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;

//JwtUtil is responsible for generating JWT tokens used in authentication.
@Component
public class JwtUtil {

    // Injecting secret key from application.yml using @Value annotation
    @Value("${jwt.secret.key}")
    private String SECRET_KEY;

    /**
     * Generates a JWT token for the given user details.
     * @param userDetails - contains information about the authenticated user
     * @return A signed JWT token as a String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

        /**
     * Creates a JWT token using given claims and subject (email).
     * @param claims - data to include in token payload (custom data)
     * @param email - the subject (i.e., username or email) of the token
     * @return A signed JWT token
     */
    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder().setClaims(claims).setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) //10 hour expectation
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

}
