package com.alumniportal.unmsm.exception.NotFoundException;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
