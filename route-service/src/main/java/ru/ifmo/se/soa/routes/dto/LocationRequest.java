package ru.ifmo.se.soa.routes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

public record LocationRequest(
        @Valid
        @NotNull(message = ValidationUtils.NOT_NULL_MSG)
        CoordinatesDto coordinates,

        @NotBlank(message = ValidationUtils.NOT_BLANK_MSG)
        String name
) {
}
