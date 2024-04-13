package oop.project.cli;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ScenariosTests {

    @Nested
    class Add {

        @ParameterizedTest
        @MethodSource
        public void testAdd(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testAdd() {
            return Stream.of(
                Arguments.of("Add", "calc add 1 2", List.of(Map.of("left", 1, "right", 2))),
                Arguments.of("Missing Argument", "calc add 1", null),
                Arguments.of("Extraneous Argument", "calc add 1 2 3", null),
                Arguments.of("Not A Number", "calc add one two", null),
                Arguments.of("Not An Integer", "calc add 1.0 2.0", null)
            );
        }

    }

    @Nested
    class Sub {

        @ParameterizedTest
        @MethodSource
        public void testSub(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSub() {
            return Stream.of(
                Arguments.of("Sub", "calc sub --left 1.0 --right 2.0", List.of(Map.of("left", 1.0, "right", 2.0))),
                Arguments.of("Left Only", "calc sub --left 1.0", null),
                Arguments.of("Right Only", "calc sub --right 2.0", List.of(Map.of("left", Optional.empty(), "right", 2.0))),
                Arguments.of("Missing Value", "calc sub --right", null),
                Arguments.of("Extraneous Argument", "calc sub --right 2.0 extraneous", null),
                Arguments.of("Misspelled Flag", "calc sub --write 2.0", null),
                Arguments.of("Not A Number", "calc sub --right two", null)
            );
        }

    }

    @Nested
    class Sqrt {

        @ParameterizedTest
        @MethodSource
        public void testSqrt(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testSqrt() {
            return Stream.of(
                Arguments.of("Valid", "calc sqrt 4", List.of(Map.of("number", 4))),
                Arguments.of("Imperfect Square", "calc sqrt 3", List.of(Map.of("number", 3))),
                Arguments.of("Zero", "calc sqrt 0", List.of(Map.of("number", 0))),
                Arguments.of("Negative", "calc sqrt -1", null)
            );
        }

    }


    @Nested
    class Date {

        @ParameterizedTest
        @MethodSource
        public void testDate(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testDate() {
            return Stream.of(
                Arguments.of("Date", "date 2024-01-01", List.of(Map.of("date", LocalDate.of(2024, 1, 1)))),
                Arguments.of("Invalid", "date 20240401", null)
            );
        }
    }

    @Nested
    class Help {
        @ParameterizedTest
        @MethodSource
        public void testHelp(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testHelp() {
            return Stream.of(
                    Arguments.of("Help only", "help", List.of(Map.of("subcommand", "", "syntax", "help [<subcommand>]", "example", "help add"))),
                    Arguments.of("Help Single arg", "help add", List.of(Map.of("subcommand", "add", "syntax", "add <num1> <num2>", "example", "add 5 3"))),
                    Arguments.of("Help Multiple arg", "help add sub", null)
            );
        }
    }

    @Nested
    class Multiple {

        @ParameterizedTest
        @MethodSource
        public void testMultiple(String name, String command, Object expected) {
            test(command, expected);
        }

        public static Stream<Arguments> testMultiple() {
            return Stream.of(
                    Arguments.of("Multiple calc", "calc add 3 4 sub --left 1.0 --right 2.0", List.of(Map.of("left",3, "right", 4), Map.of("left", 1.0, "right", 2.0)))

            );
        }

    }


    private static void test(String command, Object expected) {
        if (expected != null) {
            var result = Parser.parse(command);
            Assertions.assertEquals(expected, result);
        } else {
            //TODO: Update with your specific Exception class or whatever other
            //error handling model you use to check for specific library issues.
            Assertions.assertThrows(Exception.class, () -> {
                Parser.parse(command);
            });
        }
    }

}
