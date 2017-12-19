package com.github.tcnh.advent;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day18 {
    private static Map<String, Long> registers = new HashMap<>();
    private static List<Long> played = new ArrayList<>();
    private static long recovered = 0;
    private static long currentInstruction = 0;

    private static Queue<Long> queue1 = new LinkedList<>();
    private static Queue<Long> queue2 = new LinkedList<>();

    static long firstAnswer() {
        populateRegisters();
        List<String> instructions = lines();
        while (recovered == 0) {
            String instruction = instructions.get((int) currentInstruction);
            String[] instr = instruction.split(" ");

            if (instr.length < 3) {
                executeInstruction(instr[0], instr[1], 0);
            } else {
                long valB = isNumber(instr[2]) ? Integer.parseInt(instr[2]) : registers.get(instr[2]);
                executeInstruction(instr[0], instr[1], valB);
            }

        }
        return recovered;
    }


    private static boolean isNumber(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void populateRegisters() {
        for (char r = 'a'; r <= 'z'; r++) {
            registers.put(String.valueOf(r), 0L);
        }
    }

    private static void executeInstruction(String instr, String register, long value) {
        switch (instr) {
            case "snd":
                played.add(registers.get(register));
                break;
            case "set":
                registers.put(register, value);
                break;
            case "add":
                registers.put(register, registers.get(register) + value);
                break;
            case "mul":
                registers.put(register, registers.get(register) * value);
                break;
            case "mod":
                registers.put(register, registers.get(register) % value);
                break;
            case "rcv":
                if ((isNumber(register) && Integer.parseInt(register) > 0) || registers.get(register) > 0) {
                    recovered = played.get(played.size() - 1);
                }
                break;
            case "jgz":
                if ((isNumber(register) && Integer.parseInt(register) > 0) || registers.get(register) > 0) {
                    currentInstruction = currentInstruction + value;
                    return;
                }
                break;

        }
        currentInstruction++;
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day18.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }


}
