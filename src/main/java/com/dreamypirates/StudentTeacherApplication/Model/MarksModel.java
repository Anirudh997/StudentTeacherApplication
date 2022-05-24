package com.dreamypirates.StudentTeacherApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MarksModel {
    private String studentEmail;
    private int physics;
    private int maths;
    private int chemistry;

}
