package com.siteshkumar.bms.Security;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.siteshkumar.bms.Entity.UserEntity;

public class CustomUserDetails implements UserDetails{

    private final Long id;
    private final String email;
    private final String password;
    private final List<GrantedAuthority> authorities;

    public CustomUserDetails(UserEntity user){
        this.id = user.getUserId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = List.of(() -> user.getRoles());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;              // We are treating email as a username
    } 
}
