package com.github.tcnh.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Day7 {

    private static Map<String, Node> nodeList = new HashMap<>();
    private static String firstNode;
    private static List<String> allChildren = new ArrayList<>();

    static String firstAnswer() {
        List<String> input = lines();
        for (String line : input) {
            line = line.replaceAll(",", "");
            String[] info = line.split(" ");
            Node node = new Node();
            node.setName(info[0]);
            node.setSize(Integer.parseInt(info[1].substring(1, info[1].length() - 1)));
            if (info.length > 2) {
                node.setHasChildren();
                String[] children = new String[info.length - 3];
                System.arraycopy(info, 3, children, 0, info.length - 3);
                node.setChildren(children);
            }
            nodeList.put(info[0], node);
        }
        List<String> candidates = new ArrayList<>();

        for (Map.Entry<String, Node> node : nodeList.entrySet()) {
            Node n = node.getValue();
            if (n.hasChildren()) {
                for (String child : n.getChildren()) {
                    Node childNode = nodeList.get(child);
                    nodeList.put(child, childNode);
                }

                candidates.add(n.getName());
                Collections.addAll(allChildren, n.getChildren());
            }
        }

        for (String candidate : candidates) {
            boolean inListOfCandidates = allChildren.contains(candidate);
            if (!inListOfCandidates) {
                firstNode = candidate;
            }
        }
        return firstNode;
    }

    static int secondAnswer() {
        int result = 0;
        String[] children = nodeList.get(firstNode).getChildren();
        Map<String, Integer> weights = new HashMap<>();
        for (Map.Entry<String, Node> n : nodeList.entrySet()) {
            int weight = getSizeOfFullColumn(n.getKey());
            weights.put(n.getKey(), weight);
        }

        while (true) {
            Map<Integer, Integer> sizeValues = new HashMap<>(children.length);
            for (String child : children) {
                int value = weights.get(child);
                if (sizeValues.containsKey(value)) {
                    sizeValues.put(value, sizeValues.get(value) + 1);
                } else {
                    sizeValues.put(value, 1);
                }
            }
            if (sizeValues.size() == 1) {
                return result;
            } else {
                int lowest = Collections.min(sizeValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
                int highest = Collections.max(sizeValues.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
                for (String child : children) {
                    int value = weights.get(child);
                    if (lowest == value) {
                        children = nodeList.get(child).getChildren();
                        int weightOfDisk = nodeList.get(child).getSize();
                        result = weightOfDisk + (highest - lowest);
                    }
                }
            }
        }

    }

    private static int getSizeOfFullColumn(String column) {
        if (nodeList.get(column).hasChildren()) {
            int size = nodeList.get(column).getSize();
            for (String child : nodeList.get(column).getChildren()) {
                size += getSizeOfFullColumn(child);
            }
            return size;
        } else {
            return nodeList.get(column).getSize();
        }
    }

        private static List<String> lines () {
        List<String> lines = new ArrayList<>();
        try {
            String inputFile = "src/main/resources/day7.input";
            Stream<String> stream = Files.lines(Paths.get(inputFile));
            lines = stream.collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return lines;
    }
    }

    class Node {
        private int size;
        private String name;
        private boolean hasChildren = false;
        private String[] children;

        boolean hasChildren() {
            return hasChildren;
        }

        void setHasChildren() {
            this.hasChildren = true;
        }

        int getSize() {
            return size;
        }

        void setSize(int size) {
            this.size = size;
        }

        String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

        String[] getChildren() {
            return children;
        }

        void setChildren(String[] children) {
            this.children = children;
        }
    }
