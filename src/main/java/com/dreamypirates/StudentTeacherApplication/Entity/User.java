package com.dreamypirates.StudentTeacherApplication.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    private String role;
    private String emailId;

    @Column(length = 60)
    private String password;
}
