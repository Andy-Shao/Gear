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
    void unlock(ReactiveDistributionLockSign sign);
    Mono<Void> unlockLater(ReactiveDistributionLockSign sign);
    Mono<Void> lock(ReactiveDistributionLockSign sign);
    Mono<Void> lock(ReactiveDistributionLockSign sign, ExpireMode mode, int times);
    Mono<Boolean> tryLock(ReactiveDistributionLockSign sign);
    Mono<Boolean> tryLock(ReactiveDistributionLockSign sign, ExpireMode mode, int times);
}
