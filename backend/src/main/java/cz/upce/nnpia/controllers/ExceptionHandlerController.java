package cz.upce.nnpia.controllers;

import cz.upce.nnpia.exceptions.InvalidCredentialException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(value = InvalidCredentialException.class)
    public ResponseEntity<String> handleInvalidCredentialException(InvalidCredentialException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

}
