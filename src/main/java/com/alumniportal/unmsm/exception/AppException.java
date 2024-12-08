package com.alumniportal.unmsm.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Getter
public class AppException extends RuntimeException {
    private final String message;
    private final String errorCode;
}