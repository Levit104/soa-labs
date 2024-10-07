package ru.ifmo.se.soa.routes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record RouteDto(
        Integer id,

        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Europe/Moscow")
        Date creationDate,

        LocationDto from,

        LocationDto to,

        Integer distance
) {
}
