package com.siteshkumar.bms.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {
    
    @NotBlank(message="username is required")
    private String username;

    @NotBlank(message="email is required")
    private String email;

    @NotBlank(message="Password is required")
    private String password;
}
