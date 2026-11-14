package com.training.departmentservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DepartmentNotFoundException.class)
    public ResponseEntity<ApiError> handleDepartmentNotFound(
            DepartmentNotFoundException ex) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                404,
                "Not Found",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateDepartmentException.class)
    public ResponseEntity<ApiError> handleDuplicateDepartment(
            DuplicateDepartmentException ex) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                409,
                "Conflict",
                ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        ApiError error = new ApiError(
                LocalDateTime.now(),
                409,
                "Conflict",
                "The requested operation violates a database constraint."
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ApiError apiError = new ApiError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                message
        );

        return ResponseEntity.badRequest().body(apiError);
    }
}