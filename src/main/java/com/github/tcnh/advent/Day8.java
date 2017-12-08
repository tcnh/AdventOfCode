package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day8 {
    private static List<String> lines = lines();
    private static HashMap<String, Integer> allRegisters = new HashMap<>();
    private static Set<Integer> allValues = new HashSet<>();

    static int firstAnswer() {
        determineAllRegisters();
        executeInstructions();
        return Collections.max(allRegisters.values());
    }

    static int secondAnswer() {
        return Collections.max(allValues);
    }

    private static void determineAllRegisters() {
        for(String line : lines) {
            String items[] = line.split(" ");
            allRegisters.put(items[0], 0);
            allRegisters.put(items[4], 0);
        }
    }

    private static void executeInstructions() {
        for(String line : lines) {
            String items[] = line.split(" ");
            String toUpdate = items[0];
            String operation = items[1];
            int modifyAmount = Integer.parseInt(items[2]);
            String conditional = items[4];
            String comparator = items[5];
            int conditionalValue = Integer.parseInt(items[6]);

            if (proceed(conditional, comparator, conditionalValue)) {
                int newAmount = 0;
                switch (operation) {
                    case "inc":
                        newAmount = allRegisters.get(toUpdate) + modifyAmount;
                        break;
                    case "dec":
                        newAmount = allRegisters.get(toUpdate) - modifyAmount;
                        break;
                }
                allRegisters.put(toUpdate, newAmount);
                allValues.add(newAmount);
            }
        }
    }

    private static boolean proceed(String conditional, String comparator, int conditionalValue) {
        switch (comparator) {
            case "==":
                return allRegisters.get(conditional) == conditionalValue;
            case "!=":
                return allRegisters.get(conditional) != conditionalValue;
            case ">":
                return allRegisters.get(conditional) > conditionalValue;
            case ">=":
                return allRegisters.get(conditional) >= conditionalValue;
            case "<":
                return allRegisters.get(conditional) < conditionalValue;
            case "<=":
                return allRegisters.get(conditional) <= conditionalValue;
            default:
                return false;
        }
    }

    private static List<String> lines () {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day8.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
