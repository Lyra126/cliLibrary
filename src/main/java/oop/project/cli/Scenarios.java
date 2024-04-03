package oop.project.cli;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

public class Scenarios {

    /**
     * Parses and returns the arguments of a command (one of the scenarios
     * below) into a Map of names to values. This method is provided as a
     * starting point that works for most groups, but depending on your command
     * structure and requirements you may need to make changes to adapt it to
     * your needs - use whatever is convenient for your design.
     */
    public static Map<String, Object> parse(String command) {
        //This assumes commands follow a similar structure to unix commands,
        //e.g. `command [arguments...]`. If your project uses a different
        //structure, e.g. Lisp syntax like `(command [arguments...])`, you may
        //need to adjust this a bit to work as expected.
        var split = command.split(" ", 2);
        var base = split[0];
        var arguments = split.length == 2 ? split[1] : "";
        return switch (base) {
            case "add" -> add(arguments);
            case "sub" -> sub(arguments);
            case "sqrt" -> sqrt(arguments);
            case "calc" -> calc(arguments);
            case "date" -> date(arguments);
            default -> throw new IllegalArgumentException("Unknown command.");
        };
    }

    /**
     * Takes two positional arguments:
     *  - {@code left: <your integer type>}
     *  - {@code right: <your integer type>}
     */
    private static Map<String, Object> add(String arguments) {
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

        return Map.of("left", left, "right", right);
    }

    /**
     * Takes two <em>named</em> arguments:
     *  - {@code left: <your decimal type>} (optional)
     *     - If your project supports default arguments, you could also parse
     *       this as a non-optional decimal value using a default of 0.0.
     *  - {@code right: <your decimal type>} (required)
     */
    private static Map<String, Object> sub(String arguments) {
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
            return Map.of("left", left, "right", right);
        } else {
            return Map.of("left", left.get(), "right", right);
        }
    }


    /**
     * Takes one positional argument:
     *  - {@code number: <your integer type>} where {@code number >= 0}
     */
    static Map<String, Object> sqrt(String arguments) {
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

        return Map.of("number", number);
    }

    /**
     * Takes one positional argument:
     *  - {@code subcommand: "add" | "div" | "sqrt" }, aka one of these values.
     *     - Note: Not all projects support subcommands, but if yours does you
     *       may want to take advantage of this scenario for that.
     */
    private static Map<String, Object> calc(String arguments) {
        arguments = arguments.trim();
        String[] parts = arguments.split(" ");

        if (parts.length != 1)
            throw new IllegalArgumentException("Invalid number of arguments for 'calc'.");

        String subcommand = parts[0];

        if (!subcommand.equals("add") && !subcommand.equals("sub") && !subcommand.equals("div") && !subcommand.equals("sqrt")) {
            throw new IllegalArgumentException("Invalid subcommand for 'calc'. Expected 'add', 'div', or 'sqrt'.");
        }

        // Return a map containing the subcommand
        return Map.of("subcommand", subcommand);
    }


    /**
     * Takes one positional argument:
     *  - {@code date: Date}, a custom type representing a {@code LocalDate}
     *    object (say at least yyyy-mm-dd, or whatever you prefer).
     *     - Note: Consider this a type that CANNOT be supported by your library
     *       out of the box and requires a custom type to be defined.
     */
    static Map<String, Object> date(String arguments) {
        //TODO: Parse arguments and extract values.
        LocalDate date = LocalDate.EPOCH;
        return Map.of("date", date);
    }

    //TODO: Add your own scenarios based on your software design writeup. You
    //should have a couple from pain points at least, and likely some others
    //for notable features. This doesn't need to be exhaustive, but this is a
    //good place to test/showcase your functionality in context.

}
