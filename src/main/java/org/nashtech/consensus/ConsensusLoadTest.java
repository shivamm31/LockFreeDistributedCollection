package org.nashtech.consensus;

import java.util.concurrent.CountDownLatch;

public class ConsensusLoadTest {

    public static void main(String[] args) throws Exception {

        ConsensusCounter consensus = new ConsensusCounter();

        int threads = 5;
        int operationsPerThread = 1000;

        long totalOperations = (long) threads * operationsPerThread;

        long start = System.currentTimeMillis();

        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                for (int j = 0; j < operationsPerThread; j++) {

                    boolean success = false;

                    while (!success) {
                        int current = consensus.current();
                        success = consensus.propose(current, current + 1);
                    }
                }
                latch.countDown();
            }).start();
        }

        latch.await();

        long end = System.currentTimeMillis();
        long timeMs = end - start;

        double avgTimePerOp = (double) timeMs * 1000 / totalOperations;

        System.out.println("===== CONSENSUS LOAD TEST RESULT =====");
        System.out.println("Total Operations : " + totalOperations);
        System.out.println("Total Time       : " + timeMs + " ms");
        System.out.println("Average Time/Op  : " + avgTimePerOp + " microseconds");
        System.out.println("Final Value      : " + consensus.current());
    }
}


