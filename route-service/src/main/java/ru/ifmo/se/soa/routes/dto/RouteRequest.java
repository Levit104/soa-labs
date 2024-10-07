package ru.ifmo.se.soa.routes.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

public record RouteRequest(
        @NotBlank(message = ValidationUtils.NOT_BLANK_MSG)
        String name,

        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        Long fromId,

        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        Long toId,

        @Min(value = ValidationUtils.MIN_DISTANCE_VAL, message = ValidationUtils.MIN_DISTANCE_MSG)
        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        Integer distance
) {
}
