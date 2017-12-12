package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day12 {
    private static Map<Integer, List<Integer>> programs = new LinkedHashMap<>();
    private static Set<Integer> connected = new HashSet<>();
    private static int groups = 0;

    static int firstAnswer() {
        populateInputMap();

        int currentProgram = 0;
        processProgram(currentProgram);
        return connected.size();
    }

    static int secondAnswer() {
        return countGroupsOfPrograms();
    }
    
    private static int countGroupsOfPrograms() {
        while (!programs.isEmpty()) {
            int currentProgram = Collections.min(programs.keySet());
            processProgram(currentProgram);
            programs.keySet().removeAll(connected);
            connected.clear();
            groups++;
        }
        return groups;
    }

    private static void processProgram(int program) {
        connected.add(program);
        if (sumOfSubRelations(program) > 0) {
            for (int i = 0; i < programs.get(program).size(); i++) {
                int currentProgram = programs.get(program).get(i);
                if (currentProgram > 0) {
                    programs.get(program).set(i, 0);
                    processProgram(currentProgram);
                }
            }
        }
    }

    private static int sumOfSubRelations(int currentProgram) {
        int sumOfSubRelations = 0;
        for (int rel : programs.get(currentProgram)) {
            if (rel > 0) {
                for (int subRel : programs.get(rel)) {
                    sumOfSubRelations += subRel;
                }
            }
        }
        return sumOfSubRelations;
    }

    private static void populateInputMap(){
        for (String line : lines()) {
            String[] info = line.split(" ");
            int program = Integer.parseInt(info[0]);
            List<Integer> connectedPrograms = new ArrayList<>();
            for (int i = 2; i < info.length; i++) {
                String strConnected = info[i].replace(",", "").trim();
                connectedPrograms.add(Integer.parseInt(strConnected));
            }
            programs.put(program, connectedPrograms);
        }
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day12.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }

}
