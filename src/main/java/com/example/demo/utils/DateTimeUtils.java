package com.example.demo.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {

    public static final String DEFAULT_DATE_FORMAT = "dd.MM.yyyy. HH:mm";

    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);

    public static LocalDateTime convertStringToLocalDateTime(String localDateTimeString) {
        return LocalDateTime.parse(localDateTimeString, formatter);
    }

}
