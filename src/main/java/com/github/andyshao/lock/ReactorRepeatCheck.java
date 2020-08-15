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
public interface ReactorRepeatCheck {
    Mono<Boolean> isRepeat(String uniqueKey, ExpireMode mode, int times);
    Mono<Boolean> isRepeat(String uniqueKey);
    ReactorDistributionLock repeatCheckLock(String uniqueKey, ExpireMode mode, int times);
}
