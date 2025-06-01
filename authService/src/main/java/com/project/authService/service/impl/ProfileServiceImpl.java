package com.project.authService.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.project.authService.service.ProfileService;
import com.project.authService.io.ProfileRequest;
import com.project.authService.io.ProfileResponse;
import com.project.authService.entity.UserEntity;
import com.project.authService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse createProfile(ProfileRequest request) {
        // Validate the request
        if (request.getName() == null || request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Name, email, and password must not be null");
        }

        // Check if the user already exists
        

        UserEntity newProfile = convertToUserEntity(request);
        if (!userRepository.existsByEmail(request.getEmail())) {
            newProfile = userRepository.save(newProfile);
            return convertToProfileResponse(newProfile);
        }
        throw new ResponseStatusException(HttpStatus.CONFLICT, "User with this email already exists");
        
    }

    private ProfileResponse convertToProfileResponse(UserEntity userEntity) {
        return ProfileResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .isAccountVerified(userEntity.getIsAccountVerified())
                .build();
    }

    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .userId(java.util.UUID.randomUUID().toString()) // Generate a unique user ID
                .email(request.getEmail())
                .password(request.getPassword()) // Password should be hashed in a real application
                .isAccountVerified(false)
                .resetOtpExpireAt(0L) // Set to null initially
                .resetOtp(null) // Set to null initially
                .verifyOtp(null) // Set to null initially
                .verifyOtpExpireAt(0L) // Set to null initially
                .build();
    }


}
