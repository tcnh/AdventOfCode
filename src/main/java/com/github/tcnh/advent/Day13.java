package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day13 {

    private static Map<Integer, Integer> firewall = new HashMap<>();

    static int firstAnswer() {
        populateFirewall();
        return getSeverityScore(0, false);
    }

    static int secondAnswer() {
        boolean pass = false;
        int waitTime = 0;
        while (!pass) {
            if(getSeverityScore(waitTime, true) > 0) {
                waitTime++;
            } else {
                pass = true;
            }
        }
        return waitTime;
    }

    private static int getSeverityScore(int waitTime, boolean exitOnFirstHit) {
        int severity = 0;
        for (int layer = 0; layer <= Collections.max(firewall.keySet()); layer++) {
            if (firewall.containsKey(layer)) {
                int scannerPositions = firewall.get(layer);
                int closedAt = 2 * (scannerPositions - 1);
                if ((layer + waitTime) % closedAt == 0) {
                    severity += layer * scannerPositions;
                    if(exitOnFirstHit) {
                        severity++;
                        break;
                    }
                }
            }
        }
        return severity;
    }

    private static void populateFirewall() {
        for (String line : lines()) {
            String[] info = line.split(": ");
            int layer = Integer.parseInt(info[0]);
            int depth = Integer.parseInt(info[1]);
            firewall.put(layer, depth);
        }
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day13.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }

}
