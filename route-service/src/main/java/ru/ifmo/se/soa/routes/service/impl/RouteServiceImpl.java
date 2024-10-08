package ru.ifmo.se.soa.routes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.entity.Location;
import ru.ifmo.se.soa.routes.entity.Route;
import ru.ifmo.se.soa.routes.exception.EntityNotFoundException;
import ru.ifmo.se.soa.routes.exception.EntityValidationException;
import ru.ifmo.se.soa.routes.mapper.RouteMapper;
import ru.ifmo.se.soa.routes.repository.RouteRepository;
import ru.ifmo.se.soa.routes.service.LocationService;
import ru.ifmo.se.soa.routes.service.RouteService;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {
    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;
    private final LocationService locationService;

    @Override
    public List<RouteDto> getAll() {
        List<Route> routes = routeRepository.findAll();
        return routeMapper.toDtoList(routes);
    }

    @Override
    public RouteDto get(Integer id) {
        Route route = findById(id);
        return routeMapper.toDto(route);
    }

    @Transactional
    @Override
    public RouteDto create(RouteRequest routeRequest, BindingResult bindingResult)
            throws EntityValidationException {
        validate(routeRequest, bindingResult);
        Route route = routeMapper.toEntity(routeRequest);
        return updateLocationsAndSave(route, routeRequest);
    }

    @Transactional
    @Override
    public RouteDto update(Integer id, RouteRequest routeRequest, BindingResult bindingResult)
            throws EntityValidationException {
        validate(routeRequest, bindingResult);
        Route route = findById(id);
        routeMapper.partialUpdate(route, routeRequest);
        return updateLocationsAndSave(route, routeRequest);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        Route route = findById(id);
        routeRepository.delete(route);
    }

    @Transactional
    @Override
    public void deleteOneByDistance(Integer distance) {
        Route route = findOneByDistance(distance);
        routeRepository.delete(route);
    }

    @Override
    public List<RouteSummary> groupByFrom() {
        return routeRepository.groupByFrom();
    }

    private Route findById(Integer id) {
        return routeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Маршрут #" + id + " не найден")
        );
    }

    private Route findOneByDistance(Integer distance) {
        return routeRepository.findOneByDistance(distance).orElseThrow(
                () -> new EntityNotFoundException("Маршрут с расстоянием " + distance + " не найден")
        );
    }

    private void validate(RouteRequest routeRequest, BindingResult bindingResult) throws EntityValidationException {
        if (Objects.equals(routeRequest.fromId(), routeRequest.toId())) {
            bindingResult.rejectValue("fromId", "", ValidationUtils.SAME_LOCATION_MSG);
            bindingResult.rejectValue("toId", "", ValidationUtils.SAME_LOCATION_MSG);
        }

        if (bindingResult.hasErrors()) {
            throw new EntityValidationException(bindingResult);
        }
    }

    private RouteDto updateLocationsAndSave(Route route, RouteRequest routeRequest) {
        // так как в запросе передаётся только from.id и to.id и остальные поля после маппинга равны null
        // + валидация, что from и to с указанными ID существуют
        Location from = locationService.find(routeRequest.fromId());
        Location to = locationService.find(routeRequest.toId());
        route.setFrom(from);
        route.setTo(to);

        routeRepository.save(route);
        return routeMapper.toDto(route);
    }
}
