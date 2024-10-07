package ru.ifmo.se.soa.routes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Coordinates {
    @Column(columnDefinition = "bigint check (x <= 251)", nullable = false)
    private Long x;

    @Column(nullable = false)
    private Integer y;

    @Column(nullable = false)
    private Long z;
}
