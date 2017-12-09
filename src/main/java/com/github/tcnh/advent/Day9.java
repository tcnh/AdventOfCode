package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day9 {
    private static int garbageCount = 0;

    static int firstAnswer() {
        String input = lines().get(0);
        input = removeIgnored(input);
        input = removeGarbage(input);
        return sumOfAllGroups(input);
    }

    static int secondAnswer() {
        return garbageCount;
    }

    private static String removeIgnored(String in) {
        while (in.contains("!")) {
            int index = in.indexOf("!");
            StringBuilder sb = new StringBuilder(in);
            sb.deleteCharAt(index + 1);
            sb.deleteCharAt(index);
            in = sb.toString();
        }
        return in;
    }

    private static String removeGarbage(String in) {
        int start = in.indexOf("<");
        int end = in.indexOf(">", start);
        while (start >= 0 && end >= 0) {
            StringBuilder sb = new StringBuilder(in);
            sb.delete(start, end + 1);
            in = sb.toString();
            garbageCount += (end - (start + 1));
            start = in.indexOf("<");
            end = in.indexOf(">", start);
        }
        return in;
    }

    private static int sumOfAllGroups(String in) {
        int depth = 0;
        int sum = 0;
        for (char c : in.toCharArray()) {
            if (c == '{') {
                depth++;
            }
            if (c == '}') {
                sum += depth;
                depth--;
            }
        }
        return sum;
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day9.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
