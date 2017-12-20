package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day20 {

    private static Map<Integer, String> particles = new HashMap<>();
    private static List<Integer> distances = new LinkedList<>();
    private static List<String> positions = new ArrayList<>();

    static int firstAnswer() {
        createParticleMap();
        for (int runs = 0; runs < 360; runs++) {
            for (int particleId : particles.keySet()) {
                updatePosition(particleId);
                updateDistances(particleId);
            }
        }
        return distances.indexOf(Collections.min(distances));
    }

    static int secondAnswer() {
        particles.clear();
        createParticleMap();

        for (int runs = 0; runs < 40; runs++) {
            positions.clear();
            for (int particleId : particles.keySet()) {
                updatePosition(particleId);
            }
            removeCollisions();
        }
        return particles.size();
    }

    private static void removeCollisions() {
        Set<String> colliding = findDuplicates();
        for (String position : colliding) {
            removeParticlesWithPosition(position);
        }
        colliding.clear();
    }

    private static void removeParticlesWithPosition(String pos) {
        List<Integer> remove = new ArrayList<>();
        for (Map.Entry<Integer, String> particle : particles.entrySet()) {
            if (particle.getValue().contains("p=<" + pos + ">")) {
                remove.add(particle.getKey());
            }
        }
        for (int p : remove) {
            particles.remove(p);
        }
    }

    private static Set<String> findDuplicates() {
        final Set<String> dupes = new HashSet<>();
        final Set<String> all = new HashSet<>();

        for (String position : positions) {
            if (!all.add(position)) {
                dupes.add(position);
            }
        }
        return dupes;
    }

    private static void updatePosition(int particleId) {
        String[] particleData = particles.get(particleId).split(", ");
        String positionData = particleData[0].substring(3, particleData[0].length() - 1);
        String velocityData = particleData[1].substring(3, particleData[1].length() - 1);
        String accelerationData = particleData[2].substring(3, particleData[2].length() - 1);
        int[] newVelocity = new int[3];
        int[] newPosition = new int[3];
        for (int i = 0; i < 3; i++) {
            int position = Integer.parseInt(positionData.split(",")[i]);
            int velocity = Integer.parseInt(velocityData.split(",")[i]);
            int acceleration = Integer.parseInt(accelerationData.split(",")[i]);
            newVelocity[i] = velocity + acceleration;
            newPosition[i] = position + newVelocity[i];
        }

        String newVelocityData = String.format("v=<%s,%s,%s>", newVelocity[0], newVelocity[1], newVelocity[2]);
        String newPositionData = String.format("p=<%s,%s,%s>", newPosition[0], newPosition[1], newPosition[2]);
        String newParticleInfo = String.format("%s, %s, a=<%s>", newPositionData, newVelocityData, accelerationData);
        particles.put(particleId, newParticleInfo);
        positions.add(newPosition[0] + "," + newPosition[1] + "," + newPosition[2]);
    }

    private static void updateDistances(int particleId) {
        String[] particleData = particles.get(particleId).split(", ");
        String positionData = particleData[0].substring(3, particleData[0].length() - 1);
        int distance = 0;
        for (String dist : positionData.split(",")) {
            distance += Math.abs(Integer.parseInt(dist));
        }
        if (distances.size() > particleId) {
            distances.remove(particleId);
        }
        distances.add(particleId, distance);
    }

    private static void createParticleMap() {
        int i = 0;
        for (String line : lines()) {
            particles.put(i, line);
            i++;
        }
    }

    private static List<String> lines() {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day20.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
}
