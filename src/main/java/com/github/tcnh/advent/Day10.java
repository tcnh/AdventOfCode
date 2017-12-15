package com.github.tcnh.advent;

import com.github.tcnh.advent.util.KnotHashBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Day10 {
    private static Integer[] input = {14, 58, 0, 116, 179, 16, 1, 104, 2, 254, 167, 86, 255, 55, 122, 244};
    private static int position = 0;
    private static int skipSize = 0;

    static int firstAnswer() {
        int listSize = 256;
        List<Integer> list = populateList(listSize);
        processHash(list, Arrays.asList(input));
        return list.get(0) * list.get(1);
    }

    private static void processHash(List<Integer> list, List<Integer> input) {
        for (int length : input) {
            List<Integer> sectionPositions = new ArrayList<>();
            List<Integer> listToReverse = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                int pos = position % list.size();
                sectionPositions.add(pos);
                listToReverse.add(list.get(pos));
                position++;
            }

            Collections.reverse(listToReverse);
            int item = 0;
            for (Integer p : sectionPositions) {
                list.set(p, listToReverse.get(item));
                item++;
            }

            position += skipSize;
            skipSize++;
        }
    }

    static String secondAnswer() {
        position = 0;
        skipSize = 0;
        String in = getInputAsString();
        KnotHashBuilder hashBuilder = new KnotHashBuilder();
        return hashBuilder.getHash(in);
    }

    private static String getInputAsString() {
        StringBuilder inputStr = new StringBuilder();
        for (Integer in : input) {
            inputStr.append(in).append(",");
        }
        inputStr.deleteCharAt(inputStr.length() - 1);
        return inputStr.toString();
    }

    private static List<Integer> populateList(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(i);
        }
        return list;
    }

}
