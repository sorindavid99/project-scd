package com.javatechie.crud.example.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(MyAppException.class)
    public ResponseEntity HandleMyAppException(final MyAppException ex){
        final Map<String, Object> responseObject = new HashMap<>();
        responseObject.put("status", ex.getStatus());
        responseObject.put("message", ex.getMessage());

        return new ResponseEntity(responseObject, HttpStatus.valueOf(ex.getStatus()));
    }
}
