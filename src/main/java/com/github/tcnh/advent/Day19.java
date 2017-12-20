package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day19 {

    private static List<String> maze = lines();
    private static String direction = "down";
    private static int row = 0;
    private static int pos = 0;
    private static int steps = 0;

    static String firstAnswer() {
        pos = maze.get(0).indexOf("|");
        steps++;
        StringBuilder result = new StringBuilder();
        while (!direction.equals("")) {
            String next = next();
            if (!next.equals("") && !next.equals(" ")) {
                result.append(next);
            }
        }
        return result.toString();
    }

    static int secondAnswer() {
        return steps;
    }

    private static String next() {
        String nextInstruction = getNextInstruction(direction);
        if (nextInstruction.equals("+")) {
            steps++;
            getNewDirection();
            next();
        } else if (nextInstruction.equals(" ")) {
            direction = "";
        } else if (!nextInstruction.equals("-") && !nextInstruction.equals("|")) {
            steps++;
            return nextInstruction;
        } else {
            steps++;
        }
        return "";
    }

    private static void getNewDirection() {
        int prevRow = row;
        int prevPos = pos;
        String[] options = new String[2];
        if (direction.equals("up") || direction.equals("down")) {
            options[0] = "right";
            options[1] = "left";
        } else {
            options[0] = "up";
            options[1] = "down";
        }

        for (String option : options) {
            String nextInstruction = getNextInstruction(option);
            pos = prevPos;
            row = prevRow;
            if (!nextInstruction.equals(" ")) {
                direction = option;
                return;
            }
        }
        direction = "";
    }

    private static String getNextInstruction(String direction) {
        String nextInstruction = " ";
        try {
            switch (direction) {
                case "up":
                    row--;
                    nextInstruction = maze.get(row).substring(pos, pos + 1);
                    break;
                case "right":
                    pos++;
                    nextInstruction = maze.get(row).substring(pos, pos + 1);
                    break;
                case "down":
                    row++;
                    nextInstruction = maze.get(row).substring(pos, pos + 1);
                    break;
                case "left":
                    pos--;
                    nextInstruction = maze.get(row).substring(pos, pos + 1);
                    break;
            }
        } catch (Exception e) {
            return nextInstruction;
        }
        return nextInstruction;

    }


    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day19.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
