package ru.ifmo.se.soa.routes.dto.search;

import jakarta.validation.constraints.Pattern;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

public record SortSpec(
        @Pattern(regexp = ValidationUtils.FIELDS, message = ValidationUtils.INVALID_SORT_FIELD)
        String field,

        @Pattern(regexp = ValidationUtils.SORT_ORDERS, message = ValidationUtils.INVALID_SORT_ORDER)
        String order
) {
}
