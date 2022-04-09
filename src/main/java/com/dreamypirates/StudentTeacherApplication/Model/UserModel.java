package com.dreamypirates.StudentTeacherApplication.Model;

import lombok.Data;

@Data
public class UserModel {
    private String firstName;
    private String lastName;
    private String emailId;
    private String role;
    private String password;
}
