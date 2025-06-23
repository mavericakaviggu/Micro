package com.project.authService.io;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "OTP must not be blank")
    private String otp;
    @NotBlank(message = "New password must not be blank")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;
}


