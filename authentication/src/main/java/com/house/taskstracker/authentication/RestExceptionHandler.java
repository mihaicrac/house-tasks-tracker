package com.house.taskstracker.authentication;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiError> handleAll(final Exception ex, final WebRequest request) {
        logger.info(ex.getClass().getName());
        logger.error("error", ex);

        ApiError apiError;
        if (ex instanceof ApiException) {
            apiError = (((ApiException) ex).createApiError());
        } else {
            apiError = new ApiError(500, "unknown internal error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}