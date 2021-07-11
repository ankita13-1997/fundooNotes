package com.microusers.noteservices.exception;

public class CollabaretorException extends RuntimeException{

    public ExceptionType exceptionType;

    public CollabaretorException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public enum ExceptionType {
        COLLABARETOR_ALREADY_PRESENT("COLLABARETOR IS ALREADY THERE"),
        COLLABARETOR_NOT_PRESENT("COLLABARETOR NOT SPECIIED !!!!"),
        COLLABARETOR_NOT_PRESENT_FOR_NOTE("COLLABARETOR NOT PRESENT FOR NOTE"),
        COLLABARETOR_ALREADY_PRESENT_FOR_NOTE("COLLABERTOR IS PRESENT FOR NOTE"),
        USER_CANNOT_BE_COLLABATED("USER CANNOT BE COLLABED");


        public String errorMsg;

        ExceptionType(String errorMsg) {
            this.errorMsg = errorMsg;
        }

    }

    public CollabaretorException(String message) {
        super(message);

    }
}
