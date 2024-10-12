package ru.ifmo.se.soa.routes.exception;

public class InvalidSearchSpecsException extends RuntimeException {
    public InvalidSearchSpecsException(String message) {
        super(message);
    }
}
