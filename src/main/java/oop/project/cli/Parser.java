package oop.project.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Parser {

    public static List<Map<String, Object>> results = new ArrayList<>();

    //parser should only take base command and create an object accordingly
    //it will return all the subcommands and arguments
    public static List<Map<String, Object>> parse(String command) {
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "calc":
                yield Calculate.parse(arguments);
            case "date":
                yield Date.date(arguments);
            case "help":
                yield Help.help(arguments);
            default:
                throw new IllegalArgumentException("Unknown command.");
        };
    }
}
