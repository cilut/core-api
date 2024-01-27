package com.cilut.coreapi.exceptions;

public class PictureNotFoundException extends RuntimeException{
    public PictureNotFoundException(String message){
        super(message);
    }
}
