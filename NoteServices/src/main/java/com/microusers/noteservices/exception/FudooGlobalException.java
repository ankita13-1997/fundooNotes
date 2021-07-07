package com.microusers.noteservices.exception;


import com.microusers.noteservices.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FudooGlobalException {



    @ExceptionHandler(NoteException.class)
    public ResponseEntity<ResponseDto> userExceptionHandler(NoteException noteException) {
        log.error("Exception Occurred : " +noteException.exceptionType.errorMsg);
        return new ResponseEntity<ResponseDto>(new ResponseDto(noteException.exceptionType.errorMsg, null,null), HttpStatus.BAD_REQUEST);
    }

}
