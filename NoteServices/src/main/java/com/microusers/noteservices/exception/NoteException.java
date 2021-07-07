package com.microusers.noteservices.exception;

public class NoteException extends RuntimeException{

    public ExceptionType exceptionType;

    public NoteException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        NOTE_ALREADY_PRESENT("NOTE IS ALREADY THERE"),
        USER_NOT_PRESENT("USER NOT SPECIIED !!!!"),
        NOTE_NOT_PRESENT("NOTE NOT AVAILABLE"),
        NOTE_ALREADY_PINNED("NOTE ALREADY PINNED"),
        NOTE_ALREADY_ARCHIEVED("NOTE ALREADY ARCHIEVED"),
        NOTE_NOT_ARCHIEVED("NOTE IS NOT ARCHIEVED"),
        NOTE_NOT_PINNED("NOTE IS NOT PINNED"),
        NOTE_NOT_TRASHED("NOTE IS NOT IN TRASH"),
        NOTE_CANNOT_BE_DELETED("NOTE IS NOT IN TRASH TO DELETED"),
        NOTE_ALREADY_IN_TRASH("NOTE ALREADY IS IN TRASH");

        public String errorMsg;

        ExceptionType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public NoteException(String message) {
        super(message);

    }
}
