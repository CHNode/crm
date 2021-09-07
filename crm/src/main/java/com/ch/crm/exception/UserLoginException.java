package com.ch.crm.exception;

public class UserLoginException extends MyUserException{
    public UserLoginException() {
        super();
    }

    public UserLoginException(String message) {
        super(message);
    }
}
