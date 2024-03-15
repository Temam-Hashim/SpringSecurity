package com.temx.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

//    bad request exception
    @ExceptionHandler(value=ApiRequestException.class)
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e){

        ApiException apiException = new ApiException(e.getMessage(),e,HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
//    not found exception
    @ExceptionHandler(value=NotFoundException.class)
    public ResponseEntity<Object> handleApiRequestException(NotFoundException e){

        ApiException apiException = new ApiException(e.getMessage(),e, HttpStatus.NOT_FOUND, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
    }

    //    alreadyExist exception
    @ExceptionHandler(value=AlreadyExistException.class)
    public ResponseEntity<Object> handleApiRequestException(AlreadyExistException e){
        ApiException apiException = new ApiException(e.getMessage(),e, HttpStatus.CONFLICT, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value=DeletedException.class)
    public ResponseEntity<Object> handleApiRequestException(DeletedException e){
        ApiException apiException = new ApiException(e.getMessage(),e, HttpStatus.GONE, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,HttpStatus.GONE);
    }

    @ExceptionHandler(value=RequestBodyRequired.class)
    public ResponseEntity<Object> handleApiRequestException(RequestBodyRequired e){
        ApiException apiException = new ApiException(e.getMessage(),e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
        return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
    }
}
