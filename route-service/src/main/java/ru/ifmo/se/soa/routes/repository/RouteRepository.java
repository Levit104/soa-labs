package ru.ifmo.se.soa.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.soa.routes.entity.Route;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Integer> {
    @NonNull
    @Query("select r from Route r left join fetch r.from f left join fetch r.to t")
    List<Route> findAll();
}
