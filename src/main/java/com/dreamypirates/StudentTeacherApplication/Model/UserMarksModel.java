package com.dreamypirates.StudentTeacherApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMarksModel {
    private String firstName;
    private String lastName;
    private String emailAddress;
    private List<MarksModel> marks;

}
