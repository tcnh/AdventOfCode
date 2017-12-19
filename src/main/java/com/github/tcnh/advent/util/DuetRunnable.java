package com.github.tcnh.advent.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DuetRunnable implements Runnable {

    private Thread t;
    Map<String, Long> registers = new HashMap<>();
    private long currentInstruction = 0;
    private BlockingQueue<Long> sendQueue;
    private BlockingQueue<Long> rcvQueue;
    boolean waiting = false;
    boolean stop = false;
    private int waitCount = 0;
    int sends = 0;
    private String threadName;
    private DuetRunnable other;

    DuetRunnable(String name, BlockingQueue<Long> sendQueue, BlockingQueue<Long> rcvQueue, long p) {
        threadName = name;
        this.sendQueue = sendQueue;
        this.rcvQueue = rcvQueue;
        populateRegisters();
        registers.put("p", p);
    }

    private boolean isNumber(String in) {
        try {
            Integer.parseInt(in);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void populateRegisters() {
        for (char r = 'a'; r <= 'z'; r++) {
            registers.put(String.valueOf(r), 0L);
        }
    }

    private void executeInstruction(String instr, String register, long value) {
        switch (instr) {
            case "snd":
                try{
                    sendQueue.offer(registers.get(register), 1, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    return;
                }
                sends++;

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
                if (rcvQueue.isEmpty()) {
                    //System.out.println(threadName + " - WAIT");
                    waiting = true;
                    return;
                } else {
                    //System.out.println(threadName + " - RCV! ");
                    waiting = false;
                    try {
                        registers.put(register, rcvQueue.take());
                    } catch (InterruptedException e) {
                        return;
                    }

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

    private List<String> lines() {
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


    @Override
    public void run() {
        List<String> instructions = lines();

        while (!(waiting && other.waiting) || !(sendQueue.size() == 0 && rcvQueue.size() == 0)) {

            String instruction = instructions.get((int) currentInstruction);
            String[] instr = instruction.split(" ");

            if (instr.length < 3) {
                executeInstruction(instr[0], instr[1], 0);
            } else {
                long valB = isNumber(instr[2]) ? Integer.parseInt(instr[2]) : registers.get(instr[2]);
                executeInstruction(instr[0], instr[1], valB);
            }

        }
        System.out.println(threadName + " - SNDS: " + sends);

    }

    void start (DuetRunnable other) {
        this.other = other;
        System.out.println("Starting " +  threadName );
        if (t == null) {
            t = new Thread (this, threadName);
            t.start ();
        }
    }

}
