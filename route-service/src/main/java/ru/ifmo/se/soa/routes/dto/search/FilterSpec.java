package ru.ifmo.se.soa.routes.dto.search;

import jakarta.validation.constraints.Pattern;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

public record FilterSpec(
        @Pattern(regexp = ValidationUtils.FIELDS, message = ValidationUtils.INVALID_FILTER_FIELD)
        String field,

        @Pattern(regexp = ValidationUtils.FILTER_OPERATIONS, message = ValidationUtils.INVALID_FILTER_OPERATION)
        String operation,

        String value
) {
}
