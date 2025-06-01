package com.project.authService.io;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileRequest {

    @NotBlank(message = "User Name cannot be blank")
    private String name;
    @Email
    @NotNull(message = "Email cannot be null")
    @NotBlank(message = "Email cannot be blank")    
    private String email;
    @Min(value = 8, message = "Password must be at least 8 characters long")
    private String password;

}
