package ru.ifmo.se.soa.routes.dto;

public record LocationDto(
        Long id,

        String name,

        CoordinatesDto coordinates
) {
}
