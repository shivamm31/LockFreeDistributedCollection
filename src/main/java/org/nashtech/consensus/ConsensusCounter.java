package org.nashtech.consensus;

public class ConsensusCounter {

    private int value = 0;

    public synchronized boolean propose(int expected, int newValue) {
        if (value == expected) {
            value = newValue;
            return true;
        }
        return false;
    }

    public synchronized int current() {
        return value;
    }
}
