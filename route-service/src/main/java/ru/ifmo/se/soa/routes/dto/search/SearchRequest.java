package ru.ifmo.se.soa.routes.dto.search;

import jakarta.validation.Valid;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchRequest(
        @Valid
        List<SortSpec> sorts,

        boolean unsorted,

        @Valid
        List<FilterSpec> filters,

        boolean anyFilter,

        @Valid
        PageSpec page
) {
}
