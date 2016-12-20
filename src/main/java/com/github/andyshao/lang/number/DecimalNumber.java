package com.github.andyshao.lang.number;

import java.math.BigDecimal;

import com.github.andyshao.lang.Convertable;

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
