package ru.ifmo.se.soa.routes.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ControllerUtils {
    private ControllerUtils() {
    }

    public static URI createLocationUri(Number id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .build(id);
    }
}
