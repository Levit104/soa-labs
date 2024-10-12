package ru.ifmo.se.soa.routes.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.entity.Route;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @Override
    @NonNull
    @EntityGraph(value = "route-fetch-join")
    List<Route> findAll();

    @EntityGraph(value = "route-fetch-join")
    @Query("select r from Route r where r.distance = :distance order by random() limit 1")
    Optional<Route> findOneByDistance(Integer distance);

    @Query("""
            select new ru.ifmo.se.soa.routes.dto.group.RouteSummary(r.from.name, count(*)) \
            from Route r group by r.from order by r.from.name
            """)
    List<RouteSummary> groupByFrom();
}
