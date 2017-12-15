package com.github.tcnh.advent;

class Day15 {

    static int firstAnswer() {
        long valA = 873;
        long valB = 583;
        int count = 0;
        for (int i = 0; i < 40000000; i++) {
            valA = (valA * 16807) % 2147483647;
            valB = (valB * 48271) % 2147483647;
            String binA = last16binary(valA);
            String binB = last16binary(valB);
            if (binA.equals(binB)) {
                count++;
            }
        }
        return count;
    }

    static int secondAnswer() {
        long valA = 873;
        long valB = 583;
        int count = 0;
        int compares = 0;
        long compA = 0;
        long compB = 0;
        while (compares <= 5000000) {
            if (compA == 0) {
                valA = (valA * 16807) % 2147483647;
                if (valA % 4 == 0) {
                    compA = valA;
                }
            }
            if (compB == 0) {
                valB = (valB * 48271) % 2147483647;
                if (valB % 8 == 0) {
                    compB = valB;
                }
            }

            if (compA > 0 && compB > 0) {
                String binA = last16binary(compA);
                String binB = last16binary(compB);
                if (binA.equals(binB)) {
                    count++;
                }
                compA = 0;
                compB = 0;
                compares++;
            }
        }
        return count;
    }

    private static String last16binary(long val) {
        StringBuilder sb = new StringBuilder(Long.toBinaryString(val));
        while (sb.length() < 16) {
            sb.insert(0, "0");
        }
        return sb.toString().substring(sb.toString().length() - 16);
    }
}
