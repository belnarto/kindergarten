package com.example.kindergarten.dto;

import java.util.Collection;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@Builder
public class UserDto implements UserDetails {

    private String username;
    private String password;
    private String role;
    private String accountNumber;
    private String address;
    private String contactPhone;

    @Pattern(regexp = "[a-zA-Z0-9_.]+@[a-z0-9]+\\.[a-z]{2,3}")
    private String email;

    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
