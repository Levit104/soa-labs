package ru.ifmo.se.soa.routes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.ifmo.se.soa.routes.dto.LocationDto;
import ru.ifmo.se.soa.routes.dto.LocationRequest;
import ru.ifmo.se.soa.routes.entity.Location;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CoordinatesMapper.class}
)
public interface LocationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "origins", ignore = true)
    @Mapping(target = "destinations", ignore = true)
    Location toEntity(LocationRequest locationRequest);

    LocationDto toDto(Location location);

    List<LocationDto> toDtoList(List<Location> locations);
}
