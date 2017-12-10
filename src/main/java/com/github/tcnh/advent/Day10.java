package com.github.tcnh.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Day10 {
    private static Integer[] input = {14,58,0,116,179,16,1,104,2,254,167,86,255,55,122,244};
    private static int listSize = 256;
    private static int position = 0;
    private static int skipSize = 0;

    static int firstAnswer() {
        List<Integer> list = populateList(listSize);
        list = processHash(list, Arrays.asList(input));

        return list.get(0) * list.get(1);

    }

    private static List<Integer> processHash(List<Integer> list, List<Integer> input) {
        for(int length : input) {
            List<Integer> sectionPositions = new ArrayList<>();
            List<Integer> listToReverse = new ArrayList<>();
            for(int i = 0; i < length; i++){
                int pos = position % list.size();
                sectionPositions.add(pos);
                listToReverse.add(list.get(pos));
                position++;
            }

            Collections.reverse(listToReverse);
            int item = 0;
            for(Integer p : sectionPositions) {
                list.set(p, listToReverse.get(item));
                item++;
            }

            position += skipSize;
            skipSize++;
        }
        return list;
    }

    static String secondAnswer() {
        position = 0;
        skipSize = 0;
        String in = getInputAsString();

        List<Integer> lengths = new ArrayList<>();
        for(char c : in.toCharArray()){
            lengths.add((int) c);
        }
        Integer[] append = {17, 31, 73, 47, 23};
        lengths.addAll(Arrays.asList(append));

        List<Integer> list = populateList(listSize);
        List<Integer> sparseHash = new ArrayList<>();
        for(int i = 0; i < 64; i++){
            sparseHash = processHash(list, lengths);
        }

        List<Integer> denseHash = getDenseHash(sparseHash);

        StringBuilder knotHash = new StringBuilder();
        for(int val : denseHash) {
            String hex = Integer.toHexString(val).length() < 2 ? "0" + Integer.toHexString(val) : Integer.toHexString(val);
            knotHash.append(hex);
        }
    return knotHash.toString();
    }

    private static List<Integer> getDenseHash(List<Integer> sparseHash) {
        int pos = 0;
        int out;
        List<Integer> denseHash = new ArrayList<>();
        while(pos < listSize) {
            out = sparseHash.get(pos);
            for(int i = pos + 1; i < pos + 16; i++) {
                out = out ^ sparseHash.get(i);
            }
            denseHash.add(out);
            pos += 16;
        }
        return denseHash;
    }

    private static String getInputAsString() {
        StringBuilder inputStr = new StringBuilder();
        for(Integer in : input) {
            inputStr.append(in).append(",");
        }
        inputStr.deleteCharAt(inputStr.length() - 1);
        return inputStr.toString();
    }

    private static List<Integer> populateList(int length) {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < length; i++) {
            list.add(i);
        }
        return list;
    }

}
