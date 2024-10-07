package ru.ifmo.se.soa.routes.exception;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class EntityValidationException extends BindException {
    public EntityValidationException(BindingResult bindingResult) {
        super(bindingResult);
    }
}
