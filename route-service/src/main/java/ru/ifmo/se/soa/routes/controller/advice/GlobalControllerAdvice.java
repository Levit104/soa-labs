package ru.ifmo.se.soa.routes.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.ifmo.se.soa.routes.dto.ApiError;
import ru.ifmo.se.soa.routes.exception.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {
    @ExceptionHandler({
            NoHandlerFoundException.class,
            EntityNotFoundException.class
    })
    public ResponseEntity<ApiError> handleNotFoundExceptions(HttpServletRequest request, Exception e) {
        return newError(request, HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            EntityValidationException.class
    })
    public ResponseEntity<ApiError> handleValidationExceptions(HttpServletRequest request, BindException e) {
        var validationErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> Optional.ofNullable(error.getDefaultMessage()).orElse("Невалидное значение")
                ));
        return newError(request, HttpStatus.UNPROCESSABLE_ENTITY, "Невалидные данные", validationErrors);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAlreadyExistsException(HttpServletRequest request, Exception e) {
        return newError(request, HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            MethodArgumentTypeMismatchException.class,
            InvalidSearchSpecsException.class
    })
    public ResponseEntity<ApiError> handleBadRequestExceptions(HttpServletRequest request, Exception e) {
        return newError(request, HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotAllowedException(HttpServletRequest request, Exception e) {
        return newError(request, HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiError> handleUnsupportedMediaTypeException(HttpServletRequest request, Exception e) {
        return newError(request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOtherExceptions(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return newError(request, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // без ошибок валидации
    private ResponseEntity<ApiError> newError(
            HttpServletRequest request, HttpStatus status, String message
    ) {
        return newError(request, status, message, null);
    }

    private ResponseEntity<ApiError> newError(
            HttpServletRequest request, HttpStatus status, String message, Map<String, String> validationErrors
    ) {
        var apiError = new ApiError(
                new Date(),
                status.value(),
                status.getReasonPhrase(),
                "%s %s".formatted(request.getMethod(), request.getRequestURI()),
                message,
                validationErrors
        );

        return ResponseEntity.status(status).body(apiError);
    }
}
