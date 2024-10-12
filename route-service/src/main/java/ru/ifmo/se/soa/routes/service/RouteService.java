package ru.ifmo.se.soa.routes.service;

import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import ru.ifmo.se.soa.routes.dto.RouteDto;
import ru.ifmo.se.soa.routes.dto.RouteRequest;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.dto.search.SearchRequest;
import ru.ifmo.se.soa.routes.exception.EntityValidationException;

import java.util.List;

public interface RouteService {
    Page<RouteDto> search(SearchRequest searchRequest);

    List<RouteDto> getAll();

    RouteDto get(Integer id);

    RouteDto create(RouteRequest routeRequest, BindingResult bindingResult) throws EntityValidationException;

    RouteDto update(Integer id, RouteRequest routeRequest, BindingResult bindingResult) throws EntityValidationException;

    void delete(Integer id);

    void deleteOneByDistance(Integer distance);

    List<RouteSummary> groupByFrom();
}
