package com.example.kindergarten.controller;

import com.example.kindergarten.dto.UserDto;
import com.example.kindergarten.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/api/user/register")
    public void register(@RequestBody @Valid UserDto user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.save(user);
    }

    @DeleteMapping("/api/user")
    public void deleteUser() {
        userService.delete();
    }

    @PutMapping("/api/user")
    public void updateUser(@RequestBody UserDto user) {

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.update(user);
    }
}
