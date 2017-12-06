package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day4 {
    static int firstAnswer() {
        int result = 0;
        List<String> lines = lines();
        for(String line : lines) {
            List<String> wordList = new ArrayList<>(Arrays.asList(line.split(" ")));
            Set<String> wordSet = new HashSet<>(Arrays.asList(line.split(" ")));

            if(wordSet.size() == wordList.size()) {
                result++;
            }
        }
        return result;
    }

    static int secondAnswer() {
        int result = 0;
        List<String> lines = lines();
        for(String line : lines) {
            List<String> wordList = new ArrayList<>(Arrays.asList(line.split(" ")));
            List<String> sortedList = new ArrayList<>();
            Set<String> sortedSet = new HashSet<>();
            for(String word : wordList) {
                word = sorted(word);
                sortedList.add(word);
                sortedSet.add(word);
            }

            if(sortedSet.size() == sortedList.size()) {
                result++;
            }
        }
        return result;
    }

    private static String sorted(String in) {
        char[] chars = in.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day4.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
