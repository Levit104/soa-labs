package ru.ifmo.se.soa.routes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ifmo.se.soa.routes.dto.LocationDto;
import ru.ifmo.se.soa.routes.dto.LocationRequest;
import ru.ifmo.se.soa.routes.entity.Location;
import ru.ifmo.se.soa.routes.exception.EntityNotFoundException;
import ru.ifmo.se.soa.routes.mapper.LocationMapper;
import ru.ifmo.se.soa.routes.repository.LocationRepository;
import ru.ifmo.se.soa.routes.service.LocationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public List<LocationDto> getAll() {
        List<Location> locations = locationRepository.findAll();
        return locationMapper.toDtoList(locations);
    }

    @Override
    public LocationDto get(Long id) {
        Location location = find(id);
        return locationMapper.toDto(location);
    }

    @Override
    public LocationDto create(LocationRequest locationRequest) {
        Location location = locationMapper.toEntity(locationRequest);
        locationRepository.save(location);
        return locationMapper.toDto(location);
    }

    @Override
    public Location find(Long id) {
        return locationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Локация #" + id + " не найдена")
        );
    }
}
