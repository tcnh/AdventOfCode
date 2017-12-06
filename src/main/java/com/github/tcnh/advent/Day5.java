package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day5 {

    static int firstAnswer() {
        int steps = 0;
        int pos = 0;
        List<Integer> values = lines();
        while (true) {
            try {
                int change = values.get(pos);
                values.set(pos, values.get(pos) + 1);
                pos = pos + change;
                steps++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return steps;
    }

    static int secondAnswer() {
        int steps = 0;
        int pos = 0;
        List<Integer> values = lines();
        while (true) {
            try {
                int change = values.get(pos);
                if(change >= 3 ) {
                    values.set(pos, values.get(pos) - 1);
                } else {
                    values.set(pos, values.get(pos) + 1);
                }
                pos = pos + change;
                steps++;
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }
        return steps;
    }


    private static List<Integer> lines() {
        List<String> strLines = new ArrayList<>();
        List<Integer> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day5.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            strLines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        for (String line : strLines) {
            lines.add(Integer.valueOf(line));
        }
        return lines;
    }
}
