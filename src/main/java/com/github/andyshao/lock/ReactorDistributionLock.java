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
    Mono<Void> unlock();
    Mono<Void> lock();
    Mono<Void> lock(ExpireMode mode, int times);
    Mono<Boolean> tryLock();
    Mono<Boolean> tryLock(ExpireMode mode, int times);
}
