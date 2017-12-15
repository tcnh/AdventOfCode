package com.github.tcnh.advent;

import com.github.tcnh.advent.util.KnotHashBuilder;

import java.util.*;

class Day14 {

    private static Map<Integer, String> grid = new HashMap<>();
    private static KnotHashBuilder hashBuilder = new KnotHashBuilder();

    static int firstAnswer() {
        populateGrid();
        return countUsedBits();
    }

    static int secondAnswer() {
        return countGroupsOfAdjacentBits();
    }

    private static void populateGrid() {
        for (int i = 0; i < 128; i++) {
            String input = "vbqugkhl";
            String knotHash = hashBuilder.getHash(input + "-" + i);
            StringBuilder data = new StringBuilder();
            for (String hexNum : knotHash.split("(?!^)")) {
                int val = Integer.parseInt(hexNum, 16);
                data.append(String.format("%4s", Integer.toBinaryString(val)).replace(' ', '0'));

            }
            grid.put(i, data.toString());
        }
    }

    private static int countUsedBits() {
        int sumOfData = 0;
        for (Map.Entry<Integer, String> row : grid.entrySet()) {
            for (String bit : row.getValue().split("(?!^)")) {
                sumOfData += Integer.valueOf(bit);
            }
        }
        return sumOfData;
    }

    private static int countGroupsOfAdjacentBits() {
        int groups = 0;
        for (int rowNo = 0; rowNo < grid.size(); rowNo++) {
            int pos = 0;
            for (String bit : grid.get(rowNo).split("(?!^)")) {
                if (bit.equals("1")) {
                    if (clearAdjacent(rowNo, pos) > 0) {
                        groups++;
                    }

                }
                pos++;
            }
        }
        return groups;
    }

    private static int clearAdjacent(int row, int col) {
        int groupSize = 0;
        String checkChar = getCharAtPos(row, col);
        if (checkChar.equals("1")) {
            groupSize++;
            removePosInRow(row, col);
            if (row > 0) {
                clearAdjacent(row - 1, col);
            }
            if (col > 0) {
                clearAdjacent(row, col - 1);
            }
            if (row < 127) {
                clearAdjacent(row + 1, col);
            }
            if (col < 127) {
                clearAdjacent(row, col + 1);
            }
        }
        return groupSize;
    }

    private static void removePosInRow(int row, int col) {
        StringBuilder rowToUpdate = new StringBuilder(grid.get(row));
        rowToUpdate.setCharAt(col, 'x');
        grid.put(row, rowToUpdate.toString());
    }

    private static String getCharAtPos(int row, int col) {
        try {
            return Character.toString(grid.get(row).charAt(col));
        } catch (Exception e) {
            return "NA";
        }
    }

}
