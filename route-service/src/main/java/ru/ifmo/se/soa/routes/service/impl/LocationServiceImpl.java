package ru.ifmo.se.soa.routes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ifmo.se.soa.routes.dto.LocationDto;
import ru.ifmo.se.soa.routes.dto.LocationRequest;
import ru.ifmo.se.soa.routes.entity.Location;
import ru.ifmo.se.soa.routes.exception.EntityAlreadyExistsException;
import ru.ifmo.se.soa.routes.exception.EntityNotFoundException;
import ru.ifmo.se.soa.routes.mapper.CoordinatesMapper;
import ru.ifmo.se.soa.routes.mapper.LocationMapper;
import ru.ifmo.se.soa.routes.repository.LocationRepository;
import ru.ifmo.se.soa.routes.service.LocationService;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    private final CoordinatesMapper coordinatesMapper;

    @Override
    public List<LocationDto> getAll() {
        var locations = locationRepository.findAll();
        return locationMapper.toDtoList(locations);
    }

    @Override
    public LocationDto get(Long id) {
        var location = find(id);
        return locationMapper.toDto(location);
    }

    @Transactional
    @Override
    public LocationDto create(LocationRequest locationRequest) {
        validate(locationRequest);
        var location = locationMapper.toEntity(locationRequest);
        locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public Location find(Long id) {
        return locationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Локация #%d не найдена".formatted(id))
        );
    }

    private void validate(LocationRequest locationRequest) {
        if (locationRepository.existsByName(locationRequest.name())) {
            throw new EntityAlreadyExistsException(ValidationUtils.LOCATION_NAME_TAKEN);
        }

        if (locationRepository.existsByCoordinates(coordinatesMapper.toEntity(locationRequest.coordinates()))) {
            throw new EntityAlreadyExistsException(ValidationUtils.LOCATION_COORDINATES_TAKEN);
        }
    }
}
