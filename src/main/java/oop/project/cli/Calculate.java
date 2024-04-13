package oop.project.cli;

import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class Calculate {
    private static String[] split;
    private static List<Map<String, Object>> results = new ArrayList<>();

    public static List<Map<String, Object>> parse(String command) {
        results.clear();
        split = command.split(" ");

        int i = 0; // Index variable to iterate over the split array
        while (i < split.length) {
            String subcommand = split[i++];
            StringBuilder argumentsBuilder = new StringBuilder();

            // Build the arguments string until the next subcommand is found
            while (i < split.length && !isSubcommand(split[i])) {
                argumentsBuilder.append(split[i++]).append(" ");
            }
            String arguments = argumentsBuilder.toString().trim();

            // Run the switch statement for the current subcommand
            switch (subcommand) {
                case "add":
                    add(arguments);
                    break;
                case "sub":
                    sub(arguments);
                    break;
                case "sqrt":
                    sqrt(arguments);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown command.");
            }
        }
        return results;
    }

    private static boolean isSubcommand(String str) {
        // Check if a string is a subcommand
        return str.equals("add") || str.equals("sub") || str.equals("sqrt");
    }


    private static void add(String arguments) {
        String[] parts = arguments.split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("add expects two arguments");
        }

        int left, right;

        try {
            left = Integer.parseInt(parts[0]);
            right = Integer.parseInt(parts[1]);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("add expects a number");
        }

        //TODO work on type checking

        results.add(Map.of("left", left, "right", right));
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    private static void sub(String arguments) {
        String[] parts = arguments.split(" ");

        Optional<Double> left = Optional.empty();
        double right = 0.0;

        boolean rightFound = false;

        for (int i = 0; i < parts.length; i += 2) {
            if (i + 1 >= parts.length) {
                throw new IllegalArgumentException("sub expects two arguments");
            }

            if (parts[i].equals("--left")) {
                left = Optional.of(Double.parseDouble(parts[i + 1]));
            } else if (parts[i].equals("--right")) {
                right = Double.parseDouble(parts[i + 1]);
                rightFound = true;
            } else {
                throw new IllegalArgumentException("Invalid argument format for sub");
            }
        }

        if (!rightFound) {
            throw new IllegalArgumentException("--right argument is required for sub");
        }

        // Return left as Optional if it exists, otherwise return it as is
        if(left.equals(Optional.empty())){
            results.add(Map.of("left", left, "right", right));
        } else {
            results.add(Map.of("left", left.get(), "right", right));
        }
    }

    static void sqrt(String arguments) {
        String[] parts = arguments.split(" ");

        if (parts.length != 1) {
            throw new IllegalArgumentException("sqrt expects one number");
        }

        int number;

        try {
            number = Integer.parseInt(parts[0]);
            if (number < 0) {
                throw new IllegalArgumentException("Number should be positive");
            }
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("sqrt expects a number");
        }

        results.add(Map.of("number", number));
    }


}
