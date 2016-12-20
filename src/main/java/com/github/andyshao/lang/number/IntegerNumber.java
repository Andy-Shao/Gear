package com.github.andyshao.lang.number;

import java.math.BigInteger;

import com.github.andyshao.lang.Convertable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Dec 20, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public interface IntegerNumber extends Number , Convertable<BigInteger> {
    default IntegerNumber add(IntegerNumber number) {
        return instance(this.convert().add(number.convert()));
    }

    default IntegerNumber divide(IntegerNumber dividend){
        return instance(this.convert().divide(dividend.convert()));
    }

    IntegerNumber instance(BigInteger bigInteger);

    default IntegerNumber multiply(IntegerNumber number){
        return instance(this.convert().multiply(number.convert()));
    }

    default IntegerNumber subtract(IntegerNumber minuend){
        return instance(this.convert().subtract(minuend.convert()));
    }
}
