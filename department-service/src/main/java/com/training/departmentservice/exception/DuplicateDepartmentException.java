package com.training.departmentservice.exception;

public class DuplicateDepartmentException extends RuntimeException {
    public DuplicateDepartmentException(String message) {
        super(message);
    }
}
