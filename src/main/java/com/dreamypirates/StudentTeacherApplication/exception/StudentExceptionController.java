package com.dreamypirates.StudentTeacherApplication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class StudentExceptionController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleResourceNotFound(final ResourceNotFoundException e, final HttpServletRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setErrorMessage(e.getMessage());
        response.setRequestUri(request.getRequestURI());
        return response;
    }
}
