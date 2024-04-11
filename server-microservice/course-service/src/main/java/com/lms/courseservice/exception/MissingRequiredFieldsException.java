package com.lms.courseservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MissingRequiredFieldsException extends RuntimeException {
    public MissingRequiredFieldsException(String missingRequiredFields) {
        super("Missing required fields");
    }
}
