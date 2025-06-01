package com.project.authService.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.project.authService.io.ProfileRequest;
import com.project.authService.io.ProfileResponse;
import com.project.authService.service.ProfileService;
import org.springframework.web.bind.annotation.ResponseStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1.0")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProfileResponse> register(@Valid @RequestBody ProfileRequest request) {
        // Logic to create a profile
        ProfileResponse response = profileService.createProfile(request);
        // send welcone email
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
