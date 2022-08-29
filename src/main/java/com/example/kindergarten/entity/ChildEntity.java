package com.example.kindergarten.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

import com.example.kindergarten.enums.Gender;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildEntity {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private int age;

    @Column(name = "SEX", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String category;
    private LocalDate birthdate;
    private LocalDateTime updatedAt;

    @ElementCollection
    private List<String> contactPhones;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
