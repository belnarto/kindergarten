package com.example.kindergarten.converter;

import com.example.kindergarten.dto.UserDto;
import com.example.kindergarten.entity.UserEntity;
import java.util.Optional;

public class UserConverter {

    public static Optional<UserEntity> toEntity(UserDto userDto) {
        if (userDto == null) {
            return Optional.empty();
        }

        UserEntity userEntity = UserEntity.builder()
            .username(userDto.getUsername())
            .password(userDto.getPassword())
            .role(userDto.getRole())
            .accountNumber(userDto.getAccountNumber())
            .address(userDto.getAddress())
                .contactPhone(userDto.getContactPhone())
            .build();

        return Optional.of(userEntity);
    }

    public static Optional<UserDto> toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }

        UserDto userDto = UserDto.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
                .accountNumber(userEntity.getAccountNumber())
                .address(userEntity.getAddress())
                .contactPhone(userEntity.getContactPhone())
                .build();
        return Optional.of(userDto);
    }

}
