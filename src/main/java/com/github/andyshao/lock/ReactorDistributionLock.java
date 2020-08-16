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
    Mono<Void> lock(ReactorDistributionLockSign sign);
    Mono<Void> lock(ReactorDistributionLockSign sign, ExpireMode mode, int times);
    Mono<Boolean> tryLock(ReactorDistributionLockSign sign);
    Mono<Boolean> tryLock(ReactorDistributionLockSign sign, ExpireMode mode, int times);
}
