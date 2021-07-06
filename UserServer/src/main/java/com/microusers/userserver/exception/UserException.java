package com.microusers.userserver.exception;

public class UserException extends RuntimeException{


    public ExceptionType exceptionType;

    public UserException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        USER_ALREADY_PRESENT("user Already present"),
        USER_NOT_FOUND("user not found"),
        INVALID_USER_ID("user id you have given is incorrect"),
        USER_NOT_PRESENT("user is not present in database"),
        EMAIL_NOT_FOUND("Enter Registered Email"),
        PASSWORD_INVALID("Invalid Password!!!Please Enter Correct Password"),
        INVALID_DATA("Please verify your email before proceeding");


        public String errorMsg;

        ExceptionType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public UserException(String message) {
        super(message);

    }
}
