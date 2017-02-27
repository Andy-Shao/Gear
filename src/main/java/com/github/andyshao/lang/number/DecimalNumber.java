package com.github.andyshao.lang.number;

import java.math.BigDecimal;

import com.github.andyshao.lang.Convertable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 27 Feb 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public interface DecimalNumber extends Number , Convertable<BigDecimal> {
    default DecimalNumber add(DecimalNumber number) {
        return this.instance(this.convert().add(number.convert()));
    }

    default DecimalNumber divide(DecimalNumber dividend) {
        return this.instance(this.convert().divide(dividend.convert()));
    }

    DecimalNumber instance(BigDecimal bigDecimal);

    default DecimalNumber multiply(DecimalNumber number) {
        return this.instance(this.convert().multiply(number.convert()));
    }

    default DecimalNumber subtract(DecimalNumber minuend) {
        return this.instance(this.convert().subtract(minuend.convert()));
    }
}
