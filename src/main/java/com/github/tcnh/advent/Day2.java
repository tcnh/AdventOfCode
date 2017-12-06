package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day2 {

    static int firstAnswer() {
        int result = 0;
        List<String> lines = lines();

        for (String line : lines) {
            String[] strArray = line.split("\t");
            List<Integer> numbers = new ArrayList<>();
            for (String str : strArray) {
                numbers.add(Integer.parseInt(str.trim()));
            }

            int min = Collections.min(numbers);
            int max = Collections.max(numbers);
            int diff = max - min;
            result += diff;
        }

        return result;
    }

    static int secondAnswer() {
        int result = 0;
        List<String> lines = lines();

        for (String line : lines) {
            String[] strArray = line.split("\t");
            List<Integer> numbers = new ArrayList<>();
            for (String str : strArray) {
                numbers.add(Integer.parseInt(str.trim()));
            }
            Collections.sort(numbers);
            Collections.reverse(numbers);

            for (int i = 0; i < numbers.size(); i++) {
                for (int j = 1; j <= numbers.size() - i - 1; j++)
                    if (numbers.get(i) % numbers.get(i + j) == 0) {
                        result += numbers.get(i) / numbers.get(i + j);
                        break;
                    }
            }

        }

        return result;

    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day2.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }

}
