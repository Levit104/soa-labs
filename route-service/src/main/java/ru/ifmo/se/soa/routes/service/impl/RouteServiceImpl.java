package ru.ifmo.se.soa.routes.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.dto.search.*;
import ru.ifmo.se.soa.routes.entity.Route;
import ru.ifmo.se.soa.routes.exception.EntityNotFoundException;
import ru.ifmo.se.soa.routes.exception.EntityValidationException;
import ru.ifmo.se.soa.routes.mapper.RouteMapper;
import ru.ifmo.se.soa.routes.repository.RouteRepository;
import ru.ifmo.se.soa.routes.service.LocationService;
import ru.ifmo.se.soa.routes.service.RouteService;
import ru.ifmo.se.soa.routes.service.search.PageSorting;
import ru.ifmo.se.soa.routes.service.search.RouteSpecification;
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
    public Page<RouteDto> search(SearchRequest searchRequest) {
        if (searchRequest == null) {
            searchRequest = SearchRequest.builder().build();
        }

        var routesPage = getSearchResult(searchRequest);

        return routesPage.map(routeMapper::toDto);
    }

    @Override
    public List<RouteDto> getAll() {
        var routes = routeRepository.findAll();
        return routeMapper.toDtoList(routes);
    }

    @Override
    public RouteDto get(Integer id) {
        var route = findById(id);
        return routeMapper.toDto(route);
    }

    @Transactional
    @Override
    public RouteDto create(RouteRequest routeRequest, BindingResult bindingResult)
            throws EntityValidationException {
        validate(routeRequest, bindingResult);
        var route = routeMapper.toEntity(routeRequest);
        return updateLocationsAndSave(route, routeRequest);
    }

    @Transactional
    @Override
    public RouteDto update(Integer id, RouteRequest routeRequest, BindingResult bindingResult)
            throws EntityValidationException {
        validate(routeRequest, bindingResult);
        var route = findById(id);
        routeMapper.partialUpdate(route, routeRequest);
        return updateLocationsAndSave(route, routeRequest);
    }

    @Transactional
    @Override
    public void delete(Integer id) {
        var route = findById(id);
        routeRepository.delete(route);
    }

    @Transactional
    @Override
    public void deleteOneByDistance(Integer distance) {
        var route = findOneByDistance(distance);
        routeRepository.delete(route);
    }

    @Override
    public List<RouteSummary> groupByFrom() {
        return routeRepository.groupByFrom();
    }

    private Page<Route> getSearchResult(SearchRequest searchRequest) {
        return routeRepository.findAll(
                RouteSpecification.applyFilters(searchRequest.filters(), searchRequest.anyFilter()),
                PageSorting.sortPage(searchRequest.sorts(), searchRequest.unsorted(), searchRequest.page())
        );
    }

    private Route findById(Integer id) {
        return routeRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Маршрут #%d не найден".formatted(id))
        );
    }

    private Route findOneByDistance(Integer distance) {
        var filter = new FilterSpec("distance", "=", distance.toString());
        var page = new PageSpec(0, 1);

        var searchRequest = SearchRequest.builder()
                .unsorted(true)
                .filters(List.of(filter))
                .page(page)
                .build();

        var routesPage = getSearchResult(searchRequest);

        if (routesPage.isEmpty()) {
            throw new EntityNotFoundException("Маршрут с расстоянием %d не найден".formatted(distance));
        }

        return routesPage.getContent().get(0);
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
        var from = locationService.find(routeRequest.fromId());
        var to = locationService.find(routeRequest.toId());
        route.setFrom(from);
        route.setTo(to);

        routeRepository.save(route);
        return routeMapper.toDto(route);
    }
}
