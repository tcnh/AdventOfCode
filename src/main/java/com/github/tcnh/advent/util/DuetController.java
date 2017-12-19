package com.github.tcnh.advent.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Tom on 18-12-2017.
 */
public class DuetController {



    public static void main(String[] args) {
        BlockingQueue<Long> queue1 = new ArrayBlockingQueue<>(100000);
        BlockingQueue<Long> queue2 = new ArrayBlockingQueue<>(100000);

        DuetRunnable prg1 = new DuetRunnable("Prog2", queue1, queue2, 0);
        DuetRunnable prg2 = new DuetRunnable("Prog1", queue2, queue1, 1);

        prg1.start(prg2);
        prg2.start(prg1);


    }

}

