package com.example.kalah.exception.handler;

import com.example.kalah.dto.ErrorDetails;
import com.example.kalah.exception.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * The type Global exception handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handle not found exception response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorDetails> handleNotFoundException(ValidationException exception) {
        return ResponseEntity.badRequest()
                .body(ErrorDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .message(exception.getMessage())
                        .build());
    }
}
