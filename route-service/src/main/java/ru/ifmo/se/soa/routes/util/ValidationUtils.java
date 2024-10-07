package ru.ifmo.se.soa.routes.util;

public class ValidationUtils {
    public static final String NOT_NULL_MSG = "Поле не может быть null";
    public static final String NOT_BLANK_MSG = "Строка не может быть пустой";

    public static final String MIN_DISTANCE_MSG = "Значение поля distance должно быть больше 1";
    public static final long MIN_DISTANCE_VAL = 2;

    public static final String MAX_COORDINATES_X_MSG = "Максимальное значение поля x: 251";
    public static final long MAX_COORDINATES_X_VAL = 251;

    public static final String SAME_LOCATION_MSG = "Локации отправления и назначения не могут совпадать";
}
