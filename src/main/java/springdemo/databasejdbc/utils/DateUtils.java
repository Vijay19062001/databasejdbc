package springdemo.databasejdbc.utils;

import springdemo.databasejdbc.exception.InvalidDateFormatException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public static LocalDate stringToLocalDate(String date) throws InvalidDateFormatException {
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException("Invalid date format: " + date);
        }
    }

    public static String localDateToString(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }
}
