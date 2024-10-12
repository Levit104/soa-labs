package ru.ifmo.se.soa.routes.service.search;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.ifmo.se.soa.routes.dto.search.FilterSpec;
import ru.ifmo.se.soa.routes.entity.Route;
import ru.ifmo.se.soa.routes.exception.InvalidSearchSpecsException;
import ru.ifmo.se.soa.routes.util.DateUtils;
import ru.ifmo.se.soa.routes.util.ValidationUtils;

import java.util.Date;
import java.util.List;

public class RouteSpecification {
    private RouteSpecification() {
    }

    public static Specification<Route> applyFilters(List<FilterSpec> filters) {
        if (filters == null || filters.isEmpty()) {
            return null;
        }

        if (ValidationUtils.hasDuplicates(filters)) {
            throw new InvalidSearchSpecsException("Дублирующиеся параметры фильтрации");
        }

        var specs = filters.stream()
                .filter(RouteSpecification::shouldApplyFilter)
                .map(RouteSpecification::addFilter)
                .toList();

        if (specs.isEmpty()) {
            throw new InvalidSearchSpecsException("Некорректные параметры фильтрации");
        }

        return Specification.allOf(specs);
    }

    private static boolean shouldApplyFilter(FilterSpec filter) {
        return ValidationUtils.notBlank(filter.field()) &&
               ValidationUtils.notBlank(filter.operation()) &&
               ValidationUtils.notBlank(filter.value());
    }

    private static Specification<Route> addFilter(FilterSpec filter) {
        var field = filter.field();
        var operation = filter.operation();
        var value = filter.value();

        return switch (field) {
            case "id" -> byId(operation, value);
            case "name" -> byName(operation, value);
            case "creationDate" -> byCreationDate(operation, value);
            case "distance" -> byDistance(operation, value);
            case "from.id" -> byFromId(operation, value);
            case "from.name" -> byFromName(operation, value);
            case "from.coordinates.x" -> byFromCoordinatesX(operation, value);
            case "from.coordinates.y" -> byFromCoordinatesY(operation, value);
            case "from.coordinates.z" -> fromCoordinatesZ(operation, value);
            case "to.id" -> toId(operation, value);
            case "to.name" -> toName(operation, value);
            case "to.coordinates.x" -> toCoordinatesX(operation, value);
            case "to.coordinates.y" -> toCoordinatesY(operation, value);
            case "to.coordinates.z" -> toCoordinatesZ(operation, value);
            default -> // валидация идёт в DTO, до сюда дойти не должно
                    throw new InvalidSearchSpecsException("Некорректное поле '%s' для фильтра".formatted(field));
        };
    }

    private static Specification<Route> byId(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("id").as(Integer.class);
            return getPredicate(cb, operation, path, validInt(value));
        };
    }

    private static Specification<Route> byName(String operation, String value) {
        validateStringOperation(operation, value);
        return (root, query, cb) -> {
            var path = root.get("name").as(String.class);
            return getPredicate(cb, operation, path, value);
        };
    }

    private static Specification<Route> byCreationDate(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("creationDate").as(Date.class);
            return getPredicate(cb, operation, path, validDate(value));
        };
    }

    private static Specification<Route> byDistance(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("distance").as(Integer.class);
            return getPredicate(cb, operation, path, validInt(value));
        };
    }

    private static Specification<Route> byFromId(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("from").get("id").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static Specification<Route> byFromName(String operation, String value) {
        validateStringOperation(operation, value);
        return (root, query, cb) -> {
            var path = root.get("from").get("name").as(String.class);
            return getPredicate(cb, operation, path, value);
        };
    }

    private static Specification<Route> byFromCoordinatesX(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("from").get("coordinates").get("x").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static Specification<Route> byFromCoordinatesY(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("from").get("coordinates").get("y").as(Integer.class);
            return getPredicate(cb, operation, path, validInt(value));
        };
    }

    private static Specification<Route> fromCoordinatesZ(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("from").get("coordinates").get("z").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static Specification<Route> toId(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("to").get("id").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static Specification<Route> toName(String operation, String value) {
        validateStringOperation(operation, value);
        return (root, query, cb) -> {
            var path = root.get("to").get("name").as(String.class);
            return getPredicate(cb, operation, path, value);
        };
    }

    private static Specification<Route> toCoordinatesX(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("to").get("coordinates").get("x").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static Specification<Route> toCoordinatesY(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("to").get("coordinates").get("y").as(Integer.class);
            return getPredicate(cb, operation, path, validInt(value));
        };
    }

    private static Specification<Route> toCoordinatesZ(String operation, String value) {
        return (root, query, cb) -> {
            var path = root.get("to").get("coordinates").get("z").as(Long.class);
            return getPredicate(cb, operation, path, validLong(value));
        };
    }

    private static <T extends Comparable<T>> Predicate getPredicate(
            CriteriaBuilder cb, String operation, Expression<T> path, T value
    ) {
        return switch (operation) {
            case "=" -> cb.equal(path, value);
            case "!=" -> cb.notEqual(path, value);
            case ">" -> cb.greaterThan(path, value);
            case ">=" -> cb.greaterThanOrEqualTo(path, value);
            case "<" -> cb.lessThan(path, value);
            case "<=" -> cb.lessThanOrEqualTo(path, value);
            default -> // валидация идёт в DTO, до сюда дойти не должно
                    throw new InvalidSearchSpecsException("Неподдерживаемая операция '%s' для фильтра".formatted(operation));
        };
    }

    private static void validateStringOperation(String operation, String value) {
        if (!ValidationUtils.isStringOperation(operation)) {
            throw new InvalidSearchSpecsException("Операция %s не применима к строке %s".formatted(operation, value));
        }
    }

    private static Integer validInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new InvalidSearchSpecsException("Некорректное значение '%s' для фильтра числа".formatted(value));
        }
    }

    private static Long validLong(String value) {
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            throw new InvalidSearchSpecsException("Некорректное значение '%s' для фильтра числа".formatted(value));
        }
    }

    private static Date validDate(String value) {
        try {
            return DateUtils.parse(value);
        } catch (IllegalArgumentException e) {
            throw new InvalidSearchSpecsException("Некорректное значение/формат '%s' для фильтра даты".formatted(value));
        }
    }
}
