package com.github.tcnh.advent.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KnotHashBuilder {
    private int listSize = 256;
    private int position = 0;
    private int skipSize = 0;


    private void processHash(List<Integer> list, List<Integer> input) {
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

    public String getHash(String input) {
        position = 0;
        skipSize = 0;

        List<Integer> lengths = new ArrayList<>();
        for (char c : input.toCharArray()) {
            lengths.add((int) c);
        }
        Integer[] append = {17, 31, 73, 47, 23};
        lengths.addAll(Arrays.asList(append));

        List<Integer> list = populateList(listSize);
        for (int i = 0; i < 64; i++) {
            processHash(list, lengths);
        }
        List<Integer> denseHash = getDenseHash(list);

        StringBuilder knotHash = new StringBuilder();
        for (int val : denseHash) {
            String hex = getHexValueFor(val);
            knotHash.append(hex);
        }

        return knotHash.toString();
    }

    private List<Integer> getDenseHash(List<Integer> sparseHash) {
        int pos = 0;
        int out;
        List<Integer> denseHash = new ArrayList<>();
        while (pos < listSize) {
            out = sparseHash.get(pos);
            for (int i = pos + 1; i < pos + 16; i++) {
                out = out ^ sparseHash.get(i);
            }
            denseHash.add(out);
            pos += 16;
        }
        return denseHash;
    }

    private String getHexValueFor(int val) {
        return Integer.toHexString(val).length() < 2 ? "0" + Integer.toHexString(val) : Integer.toHexString(val);
    }

    private List<Integer> populateList(int length) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(i);
        }
        return list;
    }

}
