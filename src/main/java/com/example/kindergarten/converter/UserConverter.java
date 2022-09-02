package com.example.kindergarten.converter;

import com.example.kindergarten.dto.ChildDto;
import com.example.kindergarten.dto.UserDto;
import com.example.kindergarten.entity.ChildEntity;
import com.example.kindergarten.entity.UserEntity;

import java.util.Optional;

public class UserConverter {

    public static Optional<UserEntity> toEntity(UserDto userDto) {
        if (userDto == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(UserEntity.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role("ROLE_USER")
                .accountNumber(userDto.getAccountNumber())
                .address(userDto.getAddress())
                .contactPhone(userDto.getContactPhone())
                .email(userDto.getEmail())
                .build());
    }

    public static Optional<UserDto> toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(UserDto.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .accountNumber(userEntity.getAccountNumber())
                .address(userEntity.getAddress())
                .contactPhone(userEntity.getContactPhone())
                .email(userEntity.getEmail())
                .build());
    }

}
