package com.dreamypirates.StudentTeacherApplication.Entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Marks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentEmail;
    private int physics;
    private int maths;
    private int chemistry;
    private int total = physics+maths+chemistry;
}
