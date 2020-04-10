package com.house.taskstracker.authentication;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends RuntimeException {
    private final int code;
    private final String message;
    private final HttpStatus status;

    public ApiError createApiError() {
        return new ApiError(code, message, status);
    }
}
