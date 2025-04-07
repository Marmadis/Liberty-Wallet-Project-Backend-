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

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<String> handleAuthException(AuthenticationException e){
        log.error("Authentication failed: {}",e.getMessage());
        return new ResponseEntity<>("Authentication failed:"+e.getMessage(),HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(SecurityJwtException.class)
    public ResponseEntity<String> handleSecurityException(SecurityJwtException e){
        log.error("An error occurred in JwtService: {}",e.getMessage());
        return new ResponseEntity<>("An error occurred with JwtToken(Service):"+e.getMessage(),HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception e){
        log.error("Unknown error occurred: {}",e.getMessage());
        return new ResponseEntity<>("An unknown error occurred:"+e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EmailNotFoundException.class)
    public ResponseEntity<String> handleEmailNotFoundException(EmailNotFoundException e){
        log.error("Email not found: {}",e.getMessage());
        return new ResponseEntity<>("User into email not found:"+e.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
        public ResponseEntity<String> handleDataBaseException(DataAccessException e){
            log.error("DataBase error: {}",e.getMessage());
            return new ResponseEntity<>("DataBase error: "+e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String> handleUserNotFoundException(NotFoundException e){
         log.error("Not found: {}",e.getMessage());
         return new ResponseEntity<>("Not found"+e.getMessage(),HttpStatus.NOT_FOUND);
        }


        @ExceptionHandler(IOException.class)
    public ResponseEntity<String> handleIOException(IOException e){
            log.error("Service unavailable: {}",e.getMessage());
            return new ResponseEntity<>("Service unavailable: "+e.getMessage(),HttpStatus.SERVICE_UNAVAILABLE);
        }

        @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalException(IllegalArgumentException e){
          log.error("Invalid input: {}",e.getMessage());
          return new ResponseEntity<>("Invalid input: "+e.getMessage(),HttpStatus.BAD_REQUEST);
        }

        @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<String> handleValueExistException(AlreadyExistException e){
         log.error("Value already exist:"+e.getMessage());
         return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }



}
