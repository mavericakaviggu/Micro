package com.project.authService.service.impl;

import com.project.authService.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.project.authService.service.ProfileService;
import com.project.authService.io.ProfileRequest;
import com.project.authService.io.ProfileResponse;
import com.project.authService.entity.UserEntity;
import com.project.authService.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.concurrent.ThreadLocalRandom;


@Service
@AllArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

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

    @Override
    public ProfileResponse getProfile(String email) {
        // Fetch the user by email
        UserEntity existingUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found:" + email));

        // Convert the entity to a response DTO
        return convertToProfileResponse(existingUser);
    }

    //convert entity to response DTO
    private ProfileResponse convertToProfileResponse(UserEntity userEntity) {
        return ProfileResponse.builder()
                .userId(userEntity.getUserId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .isAccountVerified(userEntity.getIsAccountVerified())
                .build();
    }

    @Override
    public void sendResetOtp(String email) {
        // Fetch the user by email
        UserEntity existingEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        // Generate and set 6 digit OTP
        String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000)); // Generates a random 6-digit number
       
        //calculate OTP expiration time (current time + 5 minutes in milliseconds)
        long expiryTime = System.currentTimeMillis() + (5 * 60 * 1000); // OTP expires in 5 minutes

        // Update the user entity with the OTP and its expiration time
        existingEntity.setResetOtp(otp);
        existingEntity.setResetOtpExpireAt(expiryTime);

        // Save the updated user entity
        userRepository.save(existingEntity);

        try{
            emailService.sendResetOtpEmail(existingEntity.getEmail(), otp);
        }catch(Exception ex){
            throw new RuntimeException("Unable to send reset OTP email: " + ex.getMessage());
        } 
    }

    // 🔄 Convert request DTO to entity for DB storage
    private UserEntity convertToUserEntity(ProfileRequest request) {
        return UserEntity.builder()
                .name(request.getName())
                .userId(java.util.UUID.randomUUID().toString()) // Generate a unique user ID
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // Password should be hashed in a real application
                .isAccountVerified(false)
                .resetOtpExpireAt(0L) // Set to null initially
                .resetOtp(null) // Set to null initially
                .verifyOtp(null) // Set to null initially
                .verifyOtpExpireAt(0L) // Set to null initially
                .build();
    }


}
