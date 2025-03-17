package org.example.exceptions;

public class DistanceNotValidException extends RuntimeException {
    public DistanceNotValidException(String message) {
        super(message);
    }
}
