package com.cilut.coreapi.exceptions;

public class InvalidImageTypeException extends RuntimeException{
    public InvalidImageTypeException(String message){
        super(message);
    }
}
