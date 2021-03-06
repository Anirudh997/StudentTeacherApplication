package com.dreamypirates.StudentTeacherApplication.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String password;

}
