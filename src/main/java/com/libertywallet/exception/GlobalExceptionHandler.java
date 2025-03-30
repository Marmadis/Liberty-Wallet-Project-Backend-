package com.libertywallet.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e){
        log.error("Unknown error occurred: {}",e.getMessage());
        return new ResponseEntity<>("An unknown error occurred:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException e){
        log.error("Email not found: {}",e.getMessage());
        return new ResponseEntity<>("User into email not found:"+e.getMessage(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<String> handleDataBaseException(DataAccessException e){
            log.error("DataBase error: {}",e.getMessage());
            return new ResponseEntity<>("DataBase error: "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }


        @ExceptionHandler(ChatGptServiceException.class)
    public ResponseEntity<String> handleGptServiceException(ChatGptServiceException e){
            log.error("ChatGPT service error: {}",e.getMessage());
            return new ResponseEntity<>("ChatGPT service error: "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e){
            log.error("Service unavailable: {}",e.getMessage());
            return new ResponseEntity<>("Service unavailable: "+e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
        }

        @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalException(IllegalArgumentException e){
          log.error("Invalid input: {}",e.getMessage());
          return new ResponseEntity<>("Invalid input"+e.getMessage(),HttpStatus.BAD_REQUEST);
        }




}
