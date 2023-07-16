package com.example.kindergarten.service;

import com.example.kindergarten.converter.UserConverter;
import com.example.kindergarten.dto.UserDto;
import com.example.kindergarten.repository.UserRepository;
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

    public void setAdminRole(String username) {
        userRepository.findByUsername(username)
                .map(u -> {
                    u.setRole("ROLE_ADMIN");
                    return u;
                })
                .ifPresent(userRepository::save);
    }

    public void save(UserDto userDto) {
        UserConverter.toEntity(userDto)
                .ifPresent(entity -> {
                    entity.setRole("ROLE_USER");
                    userRepository.save(entity);
                });
    }

    public void delete() {
        Authentication auth2 = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth2.getPrincipal();
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
