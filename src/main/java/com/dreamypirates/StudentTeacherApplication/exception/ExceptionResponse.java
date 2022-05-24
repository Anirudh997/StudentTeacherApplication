package com.dreamypirates.StudentTeacherApplication.exception;

import lombok.Data;

@Data
public class ExceptionResponse {
    private String errorMessage;
    private String requestUri;
}
