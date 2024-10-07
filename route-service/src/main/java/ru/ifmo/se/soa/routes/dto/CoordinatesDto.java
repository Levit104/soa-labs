package ru.ifmo.se.soa.routes.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

public record CoordinatesDto(
        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        @Max(value = ValidationUtils.MAX_COORDINATES_X_VAL, message = ValidationUtils.MAX_COORDINATES_X_MSG)
        Long x,

        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        Integer y,

        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        Long z
) {
}
