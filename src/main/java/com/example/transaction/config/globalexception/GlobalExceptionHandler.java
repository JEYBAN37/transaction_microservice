package com.example.transaction.config.globalexception;


import com.example.transaction.domain.model.exception.SupplyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";
    private static final String TIMESTAMP = "timestamp";
    private static final String DETAILS = "details";
    @ExceptionHandler(SupplyException.class)
    public ResponseEntity<Object> brandHandleBrandException(SupplyException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();

        body.put(MESSAGE, ex.getErrorMessage());
        body.put(DETAILS, request.getDescription(false));

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
