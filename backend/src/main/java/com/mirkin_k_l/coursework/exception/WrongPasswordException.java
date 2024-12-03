package com.mirkin_k_l.coursework.exception;

public class WrongPasswordException extends RuntimeException {
    public WrongPasswordException(String message){
        super(message);
    }
}
