package com.example.transaction.infrastructure.rest.advice;

import com.example.transaction.domain.model.exception.SupplyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {
    @ExceptionHandler(SupplyException.class)
    public ResponseEntity<String> handleEmptyInput(SupplyException emptyInputException){
        return new ResponseEntity<>(emptyInputException.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
}
