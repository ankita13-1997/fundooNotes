package com.microusers.userserver.exception;

import com.microusers.userserver.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class FudooGlobalException {


    @ExceptionHandler(UserException.class)
    public ResponseEntity<ResponseDto> userExceptionHandler(UserException userException) {
        log.error("Exception Occurred : " +userException.exceptionType.errorMsg);
        return new ResponseEntity<ResponseDto>(new ResponseDto(userException.exceptionType.errorMsg, null,null), HttpStatus.BAD_REQUEST);
    }

}
