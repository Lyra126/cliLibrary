package oop.project.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        if(argument == ""){
            results.add(Map.of("subcommand", "", "syntax", "help [<subcommand>]", "example", "help add"));
        }
        String[] functions = {"add", "sub", "sqrt", "calc", "date"};

        if (!isValidHelpArgument(argument, functions)) {
            throw new IllegalArgumentException("Invalid argument. Available functions: add, sub, sqrt, calc, date");
        }
        String subcommand = argument;
        String syntax;
        String example;
        switch (argument) {
            case "add":
                syntax = "add <num1> <num2>";
                example = "add 5 3";
                break;
            case "sub":
                syntax = "sub <num1> <num2>";
                example = "sub 8 2";
                break;
            case "sqrt":
                syntax = "sqrt <number>";
                example = "sqrt 16";
                break;
            case "calc":
                syntax = "calc <expression>";
                example = "calc (5 + 3) * 2";
                break;
            case "date":
                syntax = "date";
                example = "date";
                break;
            default:
                syntax = "";
                example = "";
        }

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
