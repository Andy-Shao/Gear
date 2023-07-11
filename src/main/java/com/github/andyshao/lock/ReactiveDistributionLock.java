package com.github.andyshao.lock;

import reactor.core.publisher.Mono;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright(c) 2020/8/15
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
public interface ReactiveDistributionLock {
    /**
     * unlock
     * @param sign the sign
     */
    void unlock(ReactiveDistributionLockSign sign);

    /**
     * unlock later
     * @param sign lock sign
     * @return {@link Mono}
     */
    Mono<Void> unlockLater(ReactiveDistributionLockSign sign);

    /**
     * lock
     * @param sign lock sign
     * @return {@link Mono}
     */
    Mono<Void> lock(ReactiveDistributionLockSign sign);

    /**
     * lock with time expire
     * @param sign lock sign
     * @param mode expire mode
     * @param times time value
     * @return {@link Mono}
     */
    Mono<Void> lock(ReactiveDistributionLockSign sign, ExpireMode mode, int times);

    /**
     * try lock
     * @param sign lock sign
     * @return {@link Mono}
     */
    Mono<Boolean> tryLock(ReactiveDistributionLockSign sign);

    /**
     * try lock
     * @param sign lock sign
     * @param mode expire mode
     * @param times time value
     * @return {@link Mono}
     */
    Mono<Boolean> tryLock(ReactiveDistributionLockSign sign, ExpireMode mode, int times);
}
