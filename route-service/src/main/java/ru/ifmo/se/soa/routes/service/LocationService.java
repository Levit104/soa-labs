package ru.ifmo.se.soa.routes.service;

import ru.ifmo.se.soa.routes.dto.LocationDto;
import ru.ifmo.se.soa.routes.dto.LocationRequest;
import ru.ifmo.se.soa.routes.entity.Location;

import java.util.List;

public interface LocationService {
    List<LocationDto> getAll();

    LocationDto get(Long id);

    LocationDto create(LocationRequest dto);

    Location find(Long id);
}
