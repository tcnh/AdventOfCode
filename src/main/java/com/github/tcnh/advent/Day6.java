package com.github.tcnh.advent;

import java.util.*;

class Day6 {
    private static String input = "4\t1\t15\t12\t0\t9\t9\t5\t5\t8\t7\t3\t14\t5\t12\t3";
    private static int iterations = 0;
    private static Map<String, Integer> configs = new HashMap<>();
    private static int[] currentConfig = Arrays.stream(input.split("\t"))
            .map(String::trim).mapToInt(Integer::parseInt).toArray();

    static int firstAnswer() {

        while (!configs.keySet().contains(Arrays.toString(currentConfig))) {
            configs.put(Arrays.toString(currentConfig), iterations);

            int highestIndex = getFirstMaxIndex(currentConfig);
            int valToDistribute = currentConfig[highestIndex];
            int banks = currentConfig.length;

            currentConfig[highestIndex] = 0;
            for (int i = 1; i <= valToDistribute; i++) {
                int pos = highestIndex + i;
                pos = pos % banks;
                currentConfig[pos] = currentConfig[pos] + 1;
            }
            iterations++;
        }
        return iterations;
    }

    static int secondAnswer() {
        return iterations - configs.get(Arrays.toString(currentConfig));
    }

    private static int getFirstMaxIndex(int[] arr) {
        int index = 0;
        for (int i = 0; i < arr.length; i++) {
            index = arr[i] > arr[index] ? i : index;
        }
        return index;
    }

}
