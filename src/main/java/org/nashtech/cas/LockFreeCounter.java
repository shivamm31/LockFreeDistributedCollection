package org.nashtech.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class LockFreeCounter {

    private final AtomicInteger value = new AtomicInteger();

    public void increment() {
        while (true) {
            int current = value.get();
            if (value.compareAndSet(current, current + 1))
                return;
        }
    }

    public int get() {
        return value.get();
    }
}

