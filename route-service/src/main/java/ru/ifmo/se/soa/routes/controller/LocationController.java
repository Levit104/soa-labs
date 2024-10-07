package ru.ifmo.se.soa.routes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.soa.routes.dto.LocationDto;
import ru.ifmo.se.soa.routes.dto.LocationRequest;
import ru.ifmo.se.soa.routes.service.LocationService;
import ru.ifmo.se.soa.routes.util.ControllerUtils;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/routes/locations")
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public List<LocationDto> getAll() {
        return locationService.getAll();
    }

    @GetMapping("/{id}")
    public LocationDto get(@PathVariable Long id) {
        return locationService.get(id);
    }

    @PostMapping
    public ResponseEntity<LocationDto> create(@Valid @RequestBody LocationRequest locationRequest) {
        LocationDto locationDto = locationService.create(locationRequest);
        URI uri = ControllerUtils.createLocationUri(locationDto.id());
        return ResponseEntity.created(uri).body(locationDto);
    }
}
