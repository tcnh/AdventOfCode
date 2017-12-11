package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day11 {

    private static int x = 0;
    private static int y = 0;
    private static int maxDist = 0;

    static int firstAnswer() {
        String[] steps = lines().get(0).split(",");
        for (String step : steps) {
            move(step);
        }
        return stepsToCoordinate();
    }

    static int secondAnswer() {
        return maxDist;
    }

    private static int stepsToCoordinate() {
        return Math.max(Math.abs(x), Math.abs(y));
    }

    private static void move(String direction) {
        switch (direction) {
            case "n":
                y++;
                break;
            case "ne":
                x++; y++;
                break;
            case "se":
                x++; y--;
                break;
            case "s":
                y--;
                break;
            case "sw":
                y--; x--;
                break;
            case "nw":
                x--; y++;
                break;
        }
        if (stepsToCoordinate() > maxDist) {
            maxDist = stepsToCoordinate();
        }
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day11.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
