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
    Mono<Boolean> isRepeat(ReactorDistributionLockSign sign, String uniqueKey, ExpireMode mode, int times);
    Mono<Boolean> isRepeat(ReactorDistributionLockSign sign, String uniqueKey);
    ReactorDistributionLock repeatCheckLock(ReactorDistributionLockSign sign, String uniqueKey, ExpireMode mode, int times);
}
