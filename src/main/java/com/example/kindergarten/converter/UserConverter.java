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
            .build();

        return Optional.of(userEntity);
    }

    public static Optional<UserDto> toDto(UserEntity userEntity) {
        if (userEntity == null) {
            return Optional.empty();
        }

        UserDto userDto = new UserDto(userEntity.getUsername(), userEntity.getPassword(), userEntity.getRole());
        return Optional.of(userDto);
    }

}
