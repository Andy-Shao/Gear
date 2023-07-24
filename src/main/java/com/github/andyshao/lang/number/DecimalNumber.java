package com.github.andyshao.lang.number;

import com.github.andyshao.lang.Convertible;

import java.math.BigDecimal;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 27 Feb 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface DecimalNumber extends Number , Convertible<BigDecimal> {
    /**
     * add {@link DecimalNumber}
     * @param number {@link DecimalNumber}
     * @return {@link DecimalNumber}
     */
    default DecimalNumber add(DecimalNumber number) {
        return this.instance(this.convert().add(number.convert()));
    }

    /**
     * divid with {@link DecimalNumber}
     * @param dividend {@link DecimalNumber}
     * @return {@link DecimalNumber}
     */
    default DecimalNumber divide(DecimalNumber dividend) {
        return this.instance(this.convert().divide(dividend.convert()));
    }

    /**
     * build from {@link BigDecimal}
     * @param bigDecimal {@link BigDecimal}
     * @return {@link BigDecimal}
     */
    DecimalNumber instance(BigDecimal bigDecimal);

    /**
     * multiply with {@link DecimalNumber}
     * @param number {@link DecimalNumber}
     * @return {@link DecimalNumber}
     */
    default DecimalNumber multiply(DecimalNumber number) {
        return this.instance(this.convert().multiply(number.convert()));
    }

    /**
     * substract {@link DecimalNumber}
     * @param minuend {@link DecimalNumber}
     * @return {@link DecimalNumber}
     */
    default DecimalNumber subtract(DecimalNumber minuend) {
        return this.instance(this.convert().subtract(minuend.convert()));
    }
}
