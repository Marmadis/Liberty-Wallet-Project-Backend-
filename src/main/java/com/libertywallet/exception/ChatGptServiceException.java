package com.libertywallet.exception;

public class ChatGptServiceException extends RuntimeException{
    public ChatGptServiceException(String message){
        super(message);
    }
}
