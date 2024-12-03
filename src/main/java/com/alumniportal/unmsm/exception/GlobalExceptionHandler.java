package com.alumniportal.unmsm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex) {
        HttpStatus status = getHttpStatusFromErrorCode("INVALID_CREDENTIALS");
        ErrorResponse errorResponse = new ErrorResponse("Las credenciales proporcionadas son incorrectas.");
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        HttpStatus status = getHttpStatusFromErrorCode(ex.getErrorCode());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .error(ex.getMessage())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }


    private HttpStatus getHttpStatusFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "CONFLICT" -> HttpStatus.CONFLICT;
            case "FORBIDDEN" -> HttpStatus.FORBIDDEN;
            case "TOKEN_EXPIRED" -> HttpStatus.GONE;
            case "NOT_FOUND" -> HttpStatus.NOT_FOUND;
            case "INVALID_CREDENTIALS", "UNAUTHORIZED" -> HttpStatus.UNAUTHORIZED;
            case "SERVICE_UNAVAILABLE" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

}
