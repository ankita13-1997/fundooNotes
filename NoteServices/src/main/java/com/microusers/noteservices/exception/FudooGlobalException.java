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
        return new ResponseEntity<ResponseDto>(new ResponseDto(noteException.exceptionType.errorMsg,
                null,null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LabelException.class)
    public ResponseEntity<ResponseDto> NoteResponseHandler(LabelException labelException) {
        log.error("Exception Occurred : " +labelException.exceptionType.errorMsg);
        return new ResponseEntity<ResponseDto>(new ResponseDto(labelException.exceptionType.errorMsg,
                null,null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CollabaretorException.class)
    public ResponseEntity<ResponseDto> CollabaretorResponseHandler(CollabaretorException collabException) {
        log.error("Exception Occurred : " +collabException.exceptionType.errorMsg);
        return new ResponseEntity<ResponseDto>(new ResponseDto(collabException.exceptionType.errorMsg,
                null,null), HttpStatus.BAD_REQUEST);
    }

}
