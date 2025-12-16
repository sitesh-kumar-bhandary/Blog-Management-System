package com.siteshkumar.bms.Security;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthUtils {
    
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getAllClaims(String token){
        return Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }

    public String generateAccessToken(CustomUserDetails user){
        return Jwts.builder()
                    .subject(user.getEmail())
                    .claim("userId", user.getId().toString())
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 1000*60*60))
                    .signWith(getSecretKey())
                    .compact();
    }

    public String getUsernameFromToken(String token){
        Claims claims = getAllClaims(token);
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);

        return username.equals(userDetails.getUsername()) && ! isTokenExpired(token);
    }

    public boolean isTokenExpired(String token){
        Claims claims = getAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    public CustomUserDetails getCurrentLoggedInUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // System.out.println("***************************************************");
        // System.out.println("Auth = "+auth);
        // System.out.println("***************************************************");

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserDetails)) {
            throw new AccessDeniedException("Unauthorized user");
        }

        return (CustomUserDetails) auth.getPrincipal();
    }
}
