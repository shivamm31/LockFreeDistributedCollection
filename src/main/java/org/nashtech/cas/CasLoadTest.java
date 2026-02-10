package org.nashtech.cas;

import java.util.concurrent.CountDownLatch;

public class CasLoadTest {

    public static void main(String[] args) throws Exception {

        LockFreeCounter counter = new LockFreeCounter();

        int threads = 10;
        int operationsPerThread = 100000;

        long totalOperations = (long) threads * operationsPerThread;

        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {
                    counter.increment();
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        long end = System.currentTimeMillis();
        long timeMs = end - start;

        double avgTimePerOp = (double) timeMs * 1000 / totalOperations;

        System.out.println("===== CAS LOAD TEST RESULT =====");
        System.out.println("Total Operations : " + totalOperations);
        System.out.println("Total Time       : " + timeMs + " ms");
        System.out.println("Average Time/Op  : " + avgTimePerOp + " microseconds");
        System.out.println("Final Counter    : " + counter.get());
    }
}
