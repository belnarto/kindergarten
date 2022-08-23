package com.example.kindergarten.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildDto {

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Positive
    private int age;

    @NotBlank(message = "sex should be not blank")
    private String sex;

    @NotBlank
    private String category;

    @NotNull
    private LocalDate birthdate;

    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime updatedAt;

    @NotNull
    @Size(min = 1)
    private List<String> contactPhones;
}
