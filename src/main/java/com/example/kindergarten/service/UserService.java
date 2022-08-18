package com.example.kindergarten.service;

import com.example.kindergarten.converter.UserConverter;
import com.example.kindergarten.dto.UserDto;
import com.example.kindergarten.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public void save(UserDto userDto) {
        UserConverter.toEntity(userDto)
                .ifPresent(userRepository::save);
    }

    public void delete() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        userRepository.findByUsername(userDetails.getUsername())
                .ifPresent(u -> userRepository.deleteById(u.getId()));
    }

    public void update(UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        userRepository.findByUsername(userDetails.getUsername())
                .map(u -> {
                    u.setUsername(userDto.getUsername());
                    u.setPassword(userDto.getPassword());
                    u.setRole(userDto.getRole());
                    return u;
                })
                .ifPresent(userRepository::save);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .flatMap(UserConverter::toDto)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

}
