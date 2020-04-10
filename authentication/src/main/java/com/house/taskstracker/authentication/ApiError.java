package com.house.taskstracker.authentication;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private final int code;
    private final String message;
    private final HttpStatus status;
}
