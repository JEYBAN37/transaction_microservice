package com.example.transaction.infrastructure.rest.advice;

import com.example.transaction.domain.model.exception.SupplyException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.transaction.domain.model.constant.SupplyConstant.*;

@ControllerAdvice
public class UserControllerAdvice {

    private Map<String, Object> buildResponseBody(String message, Object details) {
        Map<String, Object> body = new HashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(MESSAGE, message);
        body.put(DETAILS, details);
        return body;
    }

    @ExceptionHandler(SupplyException.class)
    public ResponseEntity<Object> handleSupplyException(SupplyException ex, WebRequest request) {
        Map<String, Object> body = buildResponseBody(ex.getErrorMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<Object> handleExpiredJwtException(ExpiredJwtException ex) {
        Map<String, Object> body = buildResponseBody("Token expired: " + ex.getMessage(), HttpStatus.UNAUTHORIZED.value());
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Object> handleMissingParams(MissingServletRequestParameterException ex) {
        Map<String, Object> body = buildResponseBody("Missing parameter: " + ex.getParameterName(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ex.getRequiredType();
        String message = String.format("Parameter '%s' expected %s",
                ex.getName(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        Map<String, Object> body = buildResponseBody(message, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        Map<String, Object> body = buildResponseBody("Unexpected error: " + ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
