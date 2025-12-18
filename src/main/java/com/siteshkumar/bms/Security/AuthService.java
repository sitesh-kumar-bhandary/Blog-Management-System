package com.siteshkumar.bms.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.siteshkumar.bms.DTO.LoginRequestDto;
import com.siteshkumar.bms.DTO.LoginResponseDto;
import com.siteshkumar.bms.DTO.SignupRequestDto;
import com.siteshkumar.bms.DTO.SignupResponseDto;
import com.siteshkumar.bms.Entity.UserEntity;
import com.siteshkumar.bms.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthUtils authUtils;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto){
        log.info("Signing up for a new user");
        UserEntity user = userRepository.findByEmail(signupRequestDto.getEmail())
                            .orElse(null);

        if(user != null)
            throw new IllegalArgumentException("User already exist");

        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());

        user = userRepository.save(UserEntity.builder()
            .username(signupRequestDto.getUsername())
            .email(signupRequestDto.getEmail())
            .password(encodedPassword)
            .roles("ROLE_USER")
            .build()
        );

        log.info("New user registered successfully!!!");

        return new SignupResponseDto(
            user.getUserId(),
            user.getUsername(),
            user.getEmail()
        );
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        
        log.info("User is trying to log-in");
        // First validate whether that user have account or not
        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequestDto.getEmail(), 
                loginRequestDto.getPassword()
            )
        );

        CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

        String token = authUtils.generateAccessToken(user);

        log.info("User logged in successfully");

        return new LoginResponseDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            token
        );
    }
}
