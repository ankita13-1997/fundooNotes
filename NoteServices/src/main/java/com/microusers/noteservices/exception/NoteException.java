package com.microusers.noteservices.exception;

public class NoteException extends RuntimeException{

    public ExceptionType exceptionType;

    public NoteException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        NOTE_ALREADY_PRESENT("NOTE IS ALREADY THERE"),
        USER_NOT_PRESENT("USER NOT SPECIIED !!!!");

        public String errorMsg;

        ExceptionType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public NoteException(String message) {
        super(message);

    }
}
