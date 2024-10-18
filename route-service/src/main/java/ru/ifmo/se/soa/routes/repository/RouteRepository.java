package ru.ifmo.se.soa.routes.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.soa.routes.dto.group.RouteSummary;
import ru.ifmo.se.soa.routes.entity.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer>, JpaSpecificationExecutor<Route> {
    @Override
    @NonNull
    @EntityGraph(value = "route-fetch-join")
    List<Route> findAll();

    @Override
    @NonNull
    @EntityGraph(value = "route-fetch-join")
    Page<Route> findAll(@Nullable Specification<Route> spec, @NonNull Pageable pageable);

    @Query("""
            select new ru.ifmo.se.soa.routes.dto.group.RouteSummary(r.from.name, count(*)) \
            from Route r group by r.from order by r.from.name
            """)
    List<RouteSummary> groupByFrom();
}
