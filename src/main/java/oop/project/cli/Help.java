package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Help {
    //help syntax:
    /*
    help //returns all functions syntax and examples
    help <add/sub/sqrt/calc/date> //can only return help log of one function at a time
    help cannot take in anything other than letters
    also can only take in one argument at a time, else it returns too many arguments
     */
    public static List<Map<String, Object>> results = new ArrayList<>();

    static List<Map<String, Object>> help(String argument) {
        results.clear();
        if(argument.equals("")){
            results.add(Map.of("subcommand", "", "syntax", "help [<subcommand>]", "example", "help add"));
            return results;
        }
        String[] functions = {"calc add", "calc sub", "calc sqrt", "calc", "date"};

        if (!isValidHelpArgument(argument, functions)) {
            throw new IllegalArgumentException("Invalid argument. Available functions: add, sub, sqrt, calc, date");
        }
        String subcommand = argument;
        String syntax;
        String example = switch (argument) {
            case "calc add" -> {
                syntax = "add <num1> <num2>";
                yield "add 5 3";
            }
            case "calc sub" -> {
                syntax = "sub <num1> <num2>";
                yield "sub 8 2";
            }
            case "calc sqrt" -> {
                syntax = "sqrt <number>";
                yield "sqrt 16";
            }
            case "calc" -> {
                syntax = "calc <expression>";
                yield "calc add";
            }
            case "date" -> {
                syntax = "date <year>-<month>-<day>";
                yield "date 2024-01-01";
            }
            default -> {
                syntax = "";
                yield "";
            }
        };

        results.add(Map.of("subcommand", subcommand, "syntax", syntax, "example", example));
        return results;
    }

    //helper function for help
    static boolean isValidHelpArgument(String argument, String[] functions) {
        for (String function : functions)
            if (function.equals(argument))
                return true;
        return false;
    }
}
