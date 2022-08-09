package com.example.demoStage2.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ChildEntity {

    @Id
    @GeneratedValue
    private long id;
    private String firstName;
    private String lastName;
    private int age;
    private String sex;
    private String category;
    private LocalDate birthdate;
    private LocalDateTime updatedAt;

    @ElementCollection
    private List<String> contactPhones;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}
