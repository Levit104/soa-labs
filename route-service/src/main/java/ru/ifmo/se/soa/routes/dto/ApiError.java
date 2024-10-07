package ru.ifmo.se.soa.routes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;
import java.util.Map;

public record ApiError(
        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
        Date timestamp,

        int status,

        String error,

        String request,

        String message,

        @JsonInclude(JsonInclude.Include.NON_NULL)
        Map<String, String> validationErrors
) {
}
