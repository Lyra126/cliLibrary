package oop.project.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Date {

    private static List<Map<String, Object>> results = new ArrayList<>();
    public static List<Map<String, Object>> parse(String command) {
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "date" -> date(arguments);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

/**
 * Takes one positional argument:
 *  - {@code date: Date}, a custom type representing a {@code LocalDate}
 *    object (say at least yyyy-mm-dd, or whatever you prefer).
 *     - Note: Consider this a type that CANNOT be supported by your library
 *       out of the box and requires a custom type to be defined.
 */
    static List<Map<String, Object>> date(String arguments) {
        String[] dateParts = arguments.split("-");
        if (dateParts.length != 3) {
            throw new IllegalArgumentException("Invalid date format. Please provide the date in yyyy-mm-dd format.");
        }
        int year, month, day;
        try {
            year = Integer.parseInt(dateParts[0]);
            month = Integer.parseInt(dateParts[1]);
            day = Integer.parseInt(dateParts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date format. Year, month, and day must be integers.");
        }
        //TODO: Parse arguments and extract values.
        LocalDate date = LocalDate.EPOCH;
        try {
            date = LocalDate.of(year, month, day);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid date components. Please provide valid year, month, and day values.");
        }
        results.add(Map.of("date", date));
        return results;
    }
}

