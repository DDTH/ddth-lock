package com.github.ddth.lock.qnd;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

import com.github.ddth.lock.AbstractLockManager;
import com.github.ddth.lock.ILockManager;
import com.github.ddth.lock.impl.InmemLockManager;

public class QndLockInmem {

    public static void main(String[] args) throws Exception {
        final int NUM_THREADS = 10;
        final Thread[] THREADS = new Thread[NUM_THREADS];
        final CountDownLatch LATCH = new CountDownLatch(NUM_THREADS);
        final AtomicInteger NUM_ACQUIRES = new AtomicInteger(0);

        final Random random = new Random(System.currentTimeMillis());
        final ILockManager lockFactory = new InmemLockManager().init();
        try {
            for (int i = 0; i < NUM_THREADS; i++) {
                THREADS[i] = new Thread(String.valueOf(i)) {
                    public void run() {
                        Lock lock = lockFactory.getLock("resource-id");
                        try {
                            final long tryTimeMs = 1000 + random.nextInt(4096);
                            System.out.println("Thread [" + getName()
                                    + "] is trying to acquire lock for " + tryTimeMs + "ms...");
                            if (lock.tryLock(tryTimeMs, TimeUnit.MILLISECONDS)) {
                                try {
                                    NUM_ACQUIRES.incrementAndGet();
                                    System.out.println("\tThread [" + getName()
                                            + "] acquired lock!");
                                    try {
                                        final long sleepTimeMs = random.nextInt(2048);
                                        System.out.println("\tThread [" + getName()
                                                + "] is sleeping for " + sleepTimeMs + "ms...");
                                        Thread.sleep(sleepTimeMs);
                                    } catch (InterruptedException e) {
                                    }
                                } finally {
                                    lock.unlock();
                                }
                            } else {
                                System.out.println("\tThread [" + getName()
                                        + "] FAILED to acquire lock!");
                            }
                        } catch (InterruptedException e) {
                            System.out.println("\tThread [" + getName()
                                    + "] FAILED to acquire lock, interrupted!");
                        }
                        LATCH.countDown();
                    }
                };
            }
            for (Thread t : THREADS) {
                t.start();
            }

            LATCH.await();
            System.out.println("==================================================");
            System.out.println("Num threads : " + NUM_THREADS);
            System.out.println("Num acquires: " + NUM_ACQUIRES);
        } finally {
            ((AbstractLockManager) lockFactory).destroy();
        }
    }
}
