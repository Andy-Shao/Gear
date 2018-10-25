package com.github.andyshao.lang.number;

import java.math.BigInteger;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 25, 2018<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class OctalIntegerNumber implements IntegerNumber {
    private static final char[] signs = new char[] {
        '0','1','2','3','4','5',
        '6','7'
    };
    private static final BigInteger radix = new BigInteger("8");
    private volatile BigInteger cache = BigInteger.ZERO;
    private volatile String valueStr = "0";

    @Override
    public int getRadix() {
        return radix.intValue();
    }

    @Override
    public String getValueStr() {
        return this.valueStr;
    }
    
    private String getValueStr01() {
        BigInteger tmp = this.cache;
        StringBuilder sb = new StringBuilder();
        while(tmp.compareTo(BigInteger.ZERO) > 0) {
            BigInteger[] divideAndRemainder = tmp.divideAndRemainder(radix);
            tmp = divideAndRemainder[0];
            sb.append(signs[divideAndRemainder[1].intValue()]);
        }
        return sb.reverse().toString();
    }

    @Override
    public boolean isDecimal() {
        return false;
    }

    @Override
    public boolean isInteger() {
        return true;
    }

    @Override
    public BigInteger convert() {
        return this.cache.add(BigInteger.ZERO);
    }

    @Override
    public OctalIntegerNumber instance(BigInteger bigInteger) {
        this.cache = bigInteger;
        this.valueStr = getValueStr01();
        return this;
    }

    @Override
    public OctalIntegerNumber instance(String valueStr) {
        cache = BigInteger.ZERO;
        for(int i=valueStr.length(),j=0; i>0; i--,j++) {
            String tmp = valueStr.substring(i - 1 , i);
            BigInteger base = radix.pow(j);
            cache = cache.add(new BigInteger(tmp).multiply(base));
        }
        this.valueStr = valueStr;
        return this;
    }
}
