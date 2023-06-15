package com.io.newschool.platform.u.u202023992.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="DNI", nullable = false)
    private Long dni;

    @Column(name="streetAddress", nullable = false)
    private String streetAddress;

    @Column(name="gender", nullable = false)
    private String gender;

    @Column(name="birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name="specialty", nullable = false)
    private String specialty;
}
