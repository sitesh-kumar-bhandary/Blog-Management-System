package com.siteshkumar.bms.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.siteshkumar.bms.DTO.LoginRequestDto;
import com.siteshkumar.bms.DTO.LoginResponseDto;
import com.siteshkumar.bms.DTO.SignupRequestDto;
import com.siteshkumar.bms.DTO.SignupResponseDto;
import com.siteshkumar.bms.Security.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/auth/")
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        SignupResponseDto signupUser = authService.signup(signupRequestDto);
        return ResponseEntity.status(201).body(signupUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginResponseDto loggedInUser = authService.login(loginRequestDto);
        return ResponseEntity.ok(loggedInUser);
    }
}
