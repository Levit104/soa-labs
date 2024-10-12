package ru.ifmo.se.soa.routes.dto.search;

import jakarta.validation.Valid;

import java.util.List;

public record SearchRequest(
        @Valid
        List<SortSpec> sorts,

        @Valid
        List<FilterSpec> filters,

        boolean anyFilter,

        @Valid
        PageSpec page
) {
}
