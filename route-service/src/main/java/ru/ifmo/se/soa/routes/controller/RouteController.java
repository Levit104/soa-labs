package ru.ifmo.se.soa.routes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.exception.EntityValidationException;
import ru.ifmo.se.soa.routes.service.RouteService;
import ru.ifmo.se.soa.routes.util.ControllerUtils;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public List<RouteDto> getAll() {
        return routeService.getAll();
    }

    @GetMapping("/{id}")
    public RouteDto get(@PathVariable Integer id) {
        return routeService.get(id);
    }

    @PostMapping
    public ResponseEntity<RouteDto> create(
            @Valid @RequestBody RouteRequest routeRequest,
            BindingResult bindingResult
    ) throws EntityValidationException {
        RouteDto routeDto = routeService.create(routeRequest, bindingResult);
        URI uri = ControllerUtils.createLocationUri(routeDto.id());
        return ResponseEntity.created(uri).body(routeDto);
    }

    @PutMapping("/{id}")
    public RouteDto update(
            @PathVariable Integer id,
            @Valid @RequestBody RouteRequest routeRequest,
            BindingResult bindingResult
    ) throws EntityValidationException {
        return routeService.update(id, routeRequest, bindingResult);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
