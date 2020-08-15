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
    void unlock();
    void lock();
    void lock(ExpireMode mode, int times);
    void lockInterruptibly() throws InterruptedException;
    void lockInterruptibly(ExpireMode mode, int times) throws InterruptedException;
    boolean tryLock();
    boolean tryLock(ExpireMode mode, int times);
}
