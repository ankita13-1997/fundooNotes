package com.microusers.noteservices.exception;

public class LabelException extends RuntimeException{

    public ExceptionType exceptionType;

    public LabelException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        LABEL_NOT_PRESENT("THE LABEL NOT THERE"),
        LABEL_ALREADY_PRESENT("THE LABEL ALREADY ADDED"),
        LABEL_ALREADY_PRESENT_FOR_NOTE("THE LABEL ALREADY NOT ADDED IN NOTE");

        public String errorMsg;

        ExceptionType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public LabelException(String message) {
        super(message);

    }

}
