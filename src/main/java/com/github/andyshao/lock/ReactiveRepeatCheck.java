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
public interface ReactiveRepeatCheck {
    /**
     * is repeated
     * @param uniqueKey unique key
     * @param mode expired mode
     * @param times time value
     * @return {@link Mono}
     */
    Mono<Boolean> isRepeat(String uniqueKey, ExpireMode mode, int times);

    /**
     * is repeated
     * @param uniqueKey unique key
     * @return {@link Mono}
     */
    Mono<Boolean> isRepeat(String uniqueKey);
}
