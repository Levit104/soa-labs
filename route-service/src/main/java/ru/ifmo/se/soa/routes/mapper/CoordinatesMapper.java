package ru.ifmo.se.soa.routes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.ifmo.se.soa.routes.dto.CoordinatesDto;
import ru.ifmo.se.soa.routes.entity.Coordinates;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CoordinatesMapper {
    Coordinates toEntity(CoordinatesDto coordinatesDto);

    CoordinatesDto toDto(Coordinates coordinates);
}
