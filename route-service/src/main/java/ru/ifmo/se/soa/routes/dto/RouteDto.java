package ru.ifmo.se.soa.routes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.ifmo.se.soa.routes.util.DateUtils;

import java.util.Date;

public record RouteDto(
        Integer id,

        String name,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_FORMAT, timezone = "Europe/Moscow")
        Date creationDate,

        LocationDto from,

        LocationDto to,

        Integer distance
) {
}
