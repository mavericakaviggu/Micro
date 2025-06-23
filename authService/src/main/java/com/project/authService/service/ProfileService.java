package com.project.authService.service;
import com.project.authService.io.ProfileRequest;
import com.project.authService.io.ProfileResponse;
import org.springframework.stereotype.Service;
import com.project.authService.service.impl.ProfileServiceImpl;



public interface ProfileService {

    
    public ProfileResponse createProfile(ProfileRequest request);

    public ProfileResponse getProfile(String email);

    void sendResetOtp(String email);

    void resetPassword(String email, String otp, String newPassword);

    void sendOtp(String email);

    void verifyOtp(String email, String otp);
}
