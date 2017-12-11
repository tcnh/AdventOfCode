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
        String input = lines().get(0);
        String[] steps = input.split(",");
        for (String step : steps) {
            move(step);
        }
        return stepsToCoordinate();
    }

    static int secondAnswer() {
        return maxDist;
    }

    private static int stepsToCoordinate() {
        int posX = Math.abs(x);
        int posY = Math.abs(y);
        int lowest = Math.min(posX, posY);
        int highest = Math.max(posX, posY);
        return lowest + (highest - lowest);
    }

    private static void move(String direction) {
        switch (direction) {
            case "n":
                y++;
                break;
            case "ne":
                x++;
                y++;
                break;
            case "e":
                x++;
                break;
            case "se":
                x++;
                y--;
                break;
            case "s":
                y--;
                break;
            case "sw":
                y--;
                x--;
                break;
            case "w":
                x--;
                break;
            case "nw":
                x--;
                y++;
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
