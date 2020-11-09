package com.example.demo.handlers;

import com.example.demo.exceptions.ResourceNotFoundDetails;
import com.example.demo.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice // to take these as default
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResourceNotFoundDetails> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        return new ResponseEntity<>(
                ResourceNotFoundDetails.builder()
                        .title("Resource not found")
                        .status(HttpStatus.NOT_FOUND.value())
                        .details(resourceNotFoundException.getMessage())
                        .localDateTime(LocalDateTime.now())
                        .message(resourceNotFoundException.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND);
    }
}
