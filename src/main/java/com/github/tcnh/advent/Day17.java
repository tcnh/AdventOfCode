package com.github.tcnh.advent;

import java.util.ArrayList;
import java.util.List;

class Day17 {


    static int firstAnswer() {
        List<Integer> buffer = new ArrayList<>();
        buffer.add(0);
        int steps = 344;
        int position = 0;
        for(int i = 1; i <= 2017; i++) {
            position = (position + steps) % buffer.size() + 1;
            buffer.add(position, i);
        }
        return buffer.get(position + 1);
    }


    static int secondAnswer() {
        int steps = 344;
        int position = 0;
        int secondNumber = 0;
        for(int i = 1; i <= 50000000; i++) {
            position = (position + 1 + steps) % i;
            if(position == 0) {
                secondNumber = i;
            }
        }
        return secondNumber;
    }
}
