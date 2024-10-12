package ru.ifmo.se.soa.routes.util;

import java.util.List;

public class ValidationUtils {
    private ValidationUtils() {
    }

    public static final String NOT_NULL_MSG = "Поле не может быть null";
    public static final String NOT_BLANK_MSG = "Строка не может быть пустой";

    public static final String MIN_DISTANCE_MSG = "Значение поля distance должно быть больше 1";
    public static final long MIN_DISTANCE_VAL = 2;

    public static final String MAX_COORDINATES_X_MSG = "Максимальное значение поля x: 251";
    public static final long MAX_COORDINATES_X_VAL = 251;

    public static final String SAME_LOCATION_MSG = "Локации отправления и назначения не могут совпадать";

    public static final String LOCATION_NAME_TAKEN = "Локация с указанным названием уже существует";

    public static final String LOCATION_COORDINATES_TAKEN = "Локация с указанными координатами уже существует";

    public static final String FIELDS = """
            ^(id|name|creationDate|distance|\
            from.id|from.name|from.coordinates.x|from.coordinates.y|from.coordinates.z|\
            to.id|to.name|to.coordinates.x|to.coordinates.y|to.coordinates.z)$\
            """;

    public static final String INVALID_FILTER_FIELD = "Некорректное поле для фильтра";
    public static final String INVALID_SORT_FIELD = "Некорректное поле для сортировки";

    public static final String FILTER_OPERATIONS = "^(=|!=|>=|<=|>|<)$";
    public static final String INVALID_FILTER_OPERATION = "Неподдерживаемая операция для фильтра";

    public static final String SORT_ORDERS = "^(ASC|DESC)$";
    public static final String INVALID_SORT_ORDER = "Некорректный порядок сортировки";

    public static boolean notBlank(String string) {
        return string != null && !string.isBlank();
    }

    public static boolean isStringOperation(String operation) {
        return operation.matches("^(=|!=)$");
    }

    public static <T> boolean hasDuplicates(List<T> list) {
        System.out.println(list);
        System.out.println(list.stream().distinct().toList());
        return list.stream().distinct().toList().size() != list.size();
    }
}
