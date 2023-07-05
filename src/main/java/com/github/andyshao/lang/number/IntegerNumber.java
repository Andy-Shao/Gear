package com.github.andyshao.lang.number;

import com.github.andyshao.lang.Convertible;

import java.math.BigInteger;

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
public interface IntegerNumber extends Number , Convertible<BigInteger> {
    /**
     * add {@link IntegerNumber}
     * @param number {@link IntegerNumber}
     * @return {@link IntegerNumber}
     */
    default IntegerNumber add(IntegerNumber number) {
        return instance(this.convert().add(number.convert()));
    }

    /**
     * divide with {@link IntegerNumber}
     * @param dividend {@link IntegerNumber}
     * @return {@link IntegerNumber}
     */
    default IntegerNumber divide(IntegerNumber dividend){
        return instance(this.convert().divide(dividend.convert()));
    }

    /**
     * get divide value and remainder value
     * @param dividend {@link IntegerNumber}
     * @return {@link IntegerNumber} array
     */
    default IntegerNumber[] divideAndRemainder(IntegerNumber dividend) {
        IntegerNumber[] result = new IntegerNumber[2];
        BigInteger[] tmp = this.convert().divideAndRemainder(dividend.convert());
        result[0] = instance(tmp[0]);
        result[1] = instance(tmp[1]);
        return result;
    }

    /**
     * build from {@link BigInteger}
     * @param bigInteger {@link BigInteger}
     * @return {@link IntegerNumber}
     */
    IntegerNumber instance(BigInteger bigInteger);

    /**
     * multiply with {@link IntegerNumber}
     * @param number {@link IntegerNumber}
     * @return {@link IntegerNumber}
     */
    default IntegerNumber multiply(IntegerNumber number){
        return instance(this.convert().multiply(number.convert()));
    }

    /**
     * substract {@link IntegerNumber}
     * @param minuend {@link IntegerNumber}
     * @return {@link IntegerNumber}
     */
    default IntegerNumber subtract(IntegerNumber minuend){
        return instance(this.convert().subtract(minuend.convert()));
    }
}
