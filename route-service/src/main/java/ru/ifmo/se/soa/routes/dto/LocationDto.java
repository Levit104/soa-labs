package ru.ifmo.se.soa.routes.dto;

public record LocationDto(
        Long id,

        CoordinatesDto coordinates,

        String name
) {
}
