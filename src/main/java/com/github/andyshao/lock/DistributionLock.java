package com.github.andyshao.lock;

/**
 * 
 * Title: Distribution lock<br>
 * Description:<br>
 * Copyright: Copyright(c) 18 Apr 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface DistributionLock {
    /**
     * unlock operation
     */
    void unlock();

    /**
     * lock operation
     */
    void lock();

    /**
     * lock with the time expire
     * @param mode the expired mode
     * @param times time value
     */
    void lock(ExpireMode mode, int times);

    /**
     * lock operation
     * @throws InterruptedException interrupted operation
     */
    void lockInterruptibly() throws InterruptedException;

    /**
     * lock with the expired time
     * @param mode expire mode
     * @param times time value
     * @throws InterruptedException interrupted operation
     */
    void lockInterruptibly(ExpireMode mode, int times) throws InterruptedException;

    /**
     * try to take a lock
     * @return if the lock has been taken then true
     */
    boolean tryLock();

    /**
     * try to take a lock
     * @param mode expired mode
     * @param times time value
     * @return if the lock has been taken then true
     */
    boolean tryLock(ExpireMode mode, int times);
}
