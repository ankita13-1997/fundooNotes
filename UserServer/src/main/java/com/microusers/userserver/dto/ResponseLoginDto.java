package com.microusers.userserver.dto;

public class ResponseLoginDto {
    public String message;
    private String statusCode;
    private Object object;
    private Object objectModel;

    public ResponseLoginDto(String message, String statusCode, Object object, Object objectModel) {
        this.message = message;
        this.statusCode = statusCode;
        this.object = object;
        this.objectModel = objectModel;
    }

    public ResponseLoginDto(Object object){
        this.object = object;
    }
}
