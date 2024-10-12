package ru.ifmo.se.soa.routes.dto.search;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PageSpec(
        @PositiveOrZero
        Integer number,

        @Positive
        Integer size
) {
}
