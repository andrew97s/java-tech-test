package com.andrew.java.juc.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author tongwenjin
 * @since 2022/7/6
 */

@Slf4j
public class CyclicBarrier {

    private static class Generation {
        boolean broken = false;
    }

    /** The lock for guarding barrier entry */
    private final ReentrantLock lock = new ReentrantLock();
    /** Condition to wait on until tripped */
    private final Condition trip = lock.newCondition();
    /** The number of parties */
    private final int parties;
    /* The command to run when tripped */
    private final Runnable barrierCommand;
    /** The current generation */
    private Generation generation = new CyclicBarrier.Generation();

    /**
     * Number of parties still waiting. Counts down from parties to 0
     * on each generation.  It is reset to parties on each new
     * generation or when broken.
     */
    private int count;

    /**
     * Updates state on barrier trip and wakes up everyone.
     * Called only while holding lock.
     */
    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
        generation = new CyclicBarrier.Generation();
    }

    /**
     * Sets current barrier generation as broken and wakes up everyone.
     * Called only while holding lock.
     */
    private void breakBarrier() {
        generation.broken = true;
        count = parties;
        trip.signalAll();
    }

    /**
     * Main barrier code, covering the various policies.
     */
    private int dowait(boolean timed, long nanos)
            throws InterruptedException, BrokenBarrierException,
            TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            log.info( "ReentrantLock({}) Locked !" , lock);
            final CyclicBarrier.Generation g = generation;

            if (g.broken) {
                throw new BrokenBarrierException();
            }

            if (Thread.interrupted()) {
                breakBarrier();
                throw new InterruptedException();
            }

            int index = --count;
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    final Runnable command = barrierCommand;
                    if (command != null) {
                        command.run();
                    }
                    ranAction = true;
                    nextGeneration();
                    return 0;
                } finally {
                    if (!ranAction) {
                        breakBarrier();
                    }
                }
            }

            // loop until tripped, broken, interrupted, or timed out
            for (;;) {
                try {
                    if (!timed)
                    {
                        trip.await();
                    }
                    else if (nanos > 0L) {
                        nanos = trip.awaitNanos(nanos);
                    }
                } catch (InterruptedException ie) {
                    if (g == generation && ! g.broken) {
                        breakBarrier();
                        throw ie;
                    } else {
                        Thread.currentThread().interrupt();
                    }
                }

                if (g.broken) {
                    throw new BrokenBarrierException();
                }

                if (g != generation) {
                    return index;
                }

                if (timed && nanos <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }
            }
        } finally {
            lock.unlock();
            log.info( "ReentrantLock({}) UnLocked !" , lock);
        }
    }


    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) {
            throw new IllegalArgumentException();
        }
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }


    public CyclicBarrier(int parties) {
        this(parties, null);
    }


    public int getParties() {
        return parties;
    }


    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0L);
        } catch (TimeoutException toe) {
            throw new Error(toe); // cannot happen
        }
    }


    public int await(long timeout, TimeUnit unit)
            throws InterruptedException,
            BrokenBarrierException,
            TimeoutException {
        return dowait(true, unit.toNanos(timeout));
    }


    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        } finally {
            lock.unlock();
        }
    }


    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();   // break the current generation
            nextGeneration(); // start a new generation
        } finally {
            lock.unlock();
        }
    }


    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}
