package ru.ifmo.se.soa.routes.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.entity.Route;

import java.util.List;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {LocationMapper.class}
)
public interface RouteMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "from", ignore = true)
    @Mapping(target = "to", ignore = true)
    Route toEntity(RouteRequest routeRequest);

    RouteDto toDto(Route route);

    List<RouteDto> toDtoList(List<Route> routes);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "creationDate", ignore = true)
    @Mapping(target = "from", ignore = true)
    @Mapping(target = "to", ignore = true)
    void partialUpdate(@MappingTarget Route route, RouteRequest routeRequest);
}
