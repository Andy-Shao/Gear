package com.github.andyshao.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Title: <br>
 * Description: <br>
 * Copyright: Copyright(c) 2020/8/16
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
@Getter
@Setter
@AllArgsConstructor
public class ReactorDistributionLockSign {
    private volatile UUID uuid;
    private volatile boolean hasLock;
}
