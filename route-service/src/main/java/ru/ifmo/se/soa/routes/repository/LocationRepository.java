package ru.ifmo.se.soa.routes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ifmo.se.soa.routes.entity.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
}
