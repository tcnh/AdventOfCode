package com.github.tcnh.advent;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

class Day3 {
    private static int input = 312051;
    private static int direction;
    private static int moves;
    private static int x;
    private static int y;
    private static Map<String, Integer> map = new HashMap<>();

    static int firstAnswer() {
        moves = 1;
        direction = 0;
        x = 0;
        y = 0;
        int i = 1;
        while (i < input) {
            for (int m = 0; m < moves; m++) {
                if (i < input) {
                    shiftMemPosition();
                    i++;
                }
            }
            changeDirection();
        }

        if (x < 0) {
            x = flip(x);
        }
        if (y < 0) {
            y = flip(y);
        }
        return x + y;
    }

    static int secondAnswer() {
        int val = 1;
        moves = 1;
        direction = 0;
        x = 0;
        y = 0;
        while (val <= input) {
            for (int m = 0; m < moves; m++) {
                if (val <= input) {
                    val = getSumOfAdjacentCells(x, y);
                    if (val == 0) {
                        val++;
                    }
                    writePositionToMap(val);
                    shiftMemPosition();
                }
            }
            changeDirection();

        }
        return val;

    }

    private static int getSumOfAdjacentCells(int x, int y) {
        int[] cells = new int[8];

        cells[0] = getCellVal(x, y - 1);
        cells[1] = getCellVal(x, y + 1);
        cells[2] = getCellVal(x + 1, y);
        cells[3] = getCellVal(x + 1, y - 1);
        cells[4] = getCellVal(x + 1, y + 1);
        cells[5] = getCellVal(x - 1, y);
        cells[6] = getCellVal(x - 1, y - 1);
        cells[7] = getCellVal(x - 1, y + 1);

        return IntStream.of(cells).sum();
    }

    private static int getCellVal(int x, int y) {
        String key = x + "," + y;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        return 0;
    }

    private static void writePositionToMap(int val) {
        String key = x + "," + y;
        map.put(key, val);
    }

    private static void shiftMemPosition() {
        switch (direction) {
            case 0:
                x++;
                break;
            case 1:
                y++;
                break;
            case 2:
                x--;
                break;
            case 3:
                y--;
                break;
        }
    }

    private static void changeDirection() {
        if (direction < 3) {
            direction = direction + 1;
        } else {
            direction = 0;
        }
        if (direction == 0 || direction == 2) {
            moves++;
        }
    }

    private static int flip(int n) {
        return 0 - n;
    }
}
