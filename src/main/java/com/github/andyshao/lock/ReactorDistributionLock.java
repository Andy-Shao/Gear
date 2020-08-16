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
public interface ReactorDistributionLock {
    void unlock(ReactorDistributionLockSign sign);
    Mono<Void> unlockLater(ReactorDistributionLockSign sign);
    Mono<ReactorDistributionLockSign> lock();
    Mono<ReactorDistributionLockSign> lock(ExpireMode mode, int times);
    Mono<ReactorDistributionLockSign> tryLock();
    Mono<ReactorDistributionLockSign> tryLock(ExpireMode mode, int times);
}
