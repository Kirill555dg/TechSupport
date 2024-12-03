package com.mirkin_k_l.coursework.exception;

import jakarta.annotation.Priority;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice(value = "com.mirkin_k_l.coursework.controller")
@Priority(0)
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MyError> catchAlreadyExistException(AlreadyExistException e){
        return new ResponseEntity<>(
                new MyError(HttpStatus.CONFLICT.value(), e.getMessage()),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler
    public ResponseEntity<MyError> catchWrongPasswordException(WrongPasswordException e){
        return new ResponseEntity<>(
                new MyError(HttpStatus.UNAUTHORIZED.value(), e.getMessage()),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler
    public ResponseEntity<MyError> catchNotFoundException(NotFoundException e) {
        return new ResponseEntity<>(
                new MyError(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
