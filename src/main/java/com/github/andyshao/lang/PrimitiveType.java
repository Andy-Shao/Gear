package com.github.andyshao.lang;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Title:<br>
 * Description:<br>
 * Copyright: Copyright(c) Jul 10, 2023<br>
 * Encoding: UNIX UTF-8
 *
 * @author Andy.Shao
 */
@RequiredArgsConstructor
public enum PrimitiveType {
    /**char type*/
    CHAR(char.class),
    /**short type*/
    SHORT(short.class),
    /**int type*/
    INT(int.class),
    /**long type*/
    LONG(long.class),
    /**float type*/
    FLOAT(float.class),
    /**double type*/
    DOUBLE(double.class),
    /**byte type*/
    BYTE(byte.class)
    ;
    @Getter
    private final Class<?> type;

    /**
     * match {@link PrimitiveType} from class
     * @param clazz {@link Class}
     * @return {@link PrimitiveType}
     */
    public static final Optional<PrimitiveType> match(Class<?> clazz) {
        if(clazz.isPrimitive()) {
            if(clazz.isAssignableFrom(char.class)) return Optional.of(CHAR);
            else if(clazz.isAssignableFrom(short.class)) return Optional.of(SHORT);
            else if(clazz.isAssignableFrom(int.class)) return Optional.of(INT);
            else if(clazz.isAssignableFrom(long.class)) return Optional.of(LONG);
            else if(clazz.isAssignableFrom(float.class)) return Optional.of(FLOAT);
            else if(clazz.isAssignableFrom(double.class)) return Optional.of(DOUBLE);
            else if(clazz.isAssignableFrom(byte.class)) return Optional.of(BYTE);
        }
        return Optional.empty();
    }
}
