package ru.ifmo.se.soa.routes.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.dto.search.SearchRequest;
import ru.ifmo.se.soa.routes.exception.EntityValidationException;
import ru.ifmo.se.soa.routes.service.RouteService;
import ru.ifmo.se.soa.routes.util.ControllerUtils;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @PostMapping("/search")
    public Page<RouteDto> search(@Valid @RequestBody(required = false) SearchRequest searchRequest) {
        return routeService.search(searchRequest);
    }

    // @GetMapping
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
        var routeDto = routeService.create(routeRequest, bindingResult);
        var uri = ControllerUtils.createLocationUri(routeDto.id());
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

    @DeleteMapping("/one/distance/{distance}")
    public ResponseEntity<Void> deleteOneByDistance(@PathVariable Integer distance) {
        routeService.deleteOneByDistance(distance);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/group/from")
    public List<RouteSummary> groupByFrom() {
        return routeService.groupByFrom();
    }
}
