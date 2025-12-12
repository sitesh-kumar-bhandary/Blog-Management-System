package com.siteshkumar.bms.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    
    private Long id;
    private String username;
    private String email;
    private String token;
}
