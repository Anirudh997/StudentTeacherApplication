package com.dreamypirates.StudentTeacherApplication.exception;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String application_not_found) {
        super(application_not_found);
    }
}
