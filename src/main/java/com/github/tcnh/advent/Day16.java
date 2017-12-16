package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day16 {

    private static List<String> programs = new ArrayList<>();
    private static int dances = 1000000000;
    private static List<String> moves = Arrays.asList(lines().get(0).split(","));
    private static List<String> results = new ArrayList<>();

    static String firstAnswer() {
        populateProgramList();
        dance();
        return String.join("", programs);
    }

    static String secondAnswer() {
        programs.clear();
        populateProgramList();
        for (int i = 0; i < dances; i++) {
            if (results.contains(String.join("", programs))) {
                return results.get(dances % i);
            }
            results.add(String.join("", programs));
            dance();
        }
        return String.join("", programs);
    }

    private static void dance() {
        for (String move : moves) {
            String action = move.substring(0, 1);
            String parties = move.substring(1);

            switch (action) {
                case "s":
                    spin(parties);
                    break;
                case "x":
                    exchange(parties);
                    break;
                case "p":
                    partner(parties);
                    break;
            }
        }
    }

    private static void spin(String party) {
        int pos = programs.size() - Integer.parseInt(party);
        int moved = 0;
        while (pos < programs.size() - moved) {
            int posToMove = (pos + moved) % programs.size();
            programs.add(moved, programs.get(posToMove));
            moved++;
            pos++;
        }
        for (int i = 0; i < moved; i++) {
            programs.remove(programs.size() - 1);
        }
    }

    private static void exchange(String parties) {
        String[] involved = parties.split("/");
        int p1 = Integer.parseInt(involved[0]);
        int p2 = Integer.parseInt(involved[1]);

        String prg1 = programs.get(p1);
        String prg2 = programs.get(p2);

        programs.set(p1, prg2);
        programs.set(p2, prg1);
    }

    private static void partner(String parties) {
        String[] involved = parties.split("/");
        String prg1 = involved[0];
        String prg2 = involved[1];

        int p1 = programs.indexOf(prg1);
        int p2 = programs.indexOf(prg2);

        programs.set(p1, prg2);
        programs.set(p2, prg1);
    }

    private static void populateProgramList() {
        String[] pgArr = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p"};
        //String[] pgArr = {"a", "b", "c", "d", "e"};
        Collections.addAll(programs, pgArr);
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day16.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
