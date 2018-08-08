package com.github.andyshao.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;

/**
 * General convert interface.
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 14, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <IN> the input
 * @param <OUT> the output
 */
@FunctionalInterface
public interface Convert<IN , OUT> {
    public static final Convert<Byte , String> BYTE_2_STR = ConvertByte2Str.byte2Char(ConvertByte2Str.BYTE_HEX);
    public static final Convert<Byte[] , String> BYTES_2_HEX = (Byte[] in) -> {
        StringBuilder builder = new StringBuilder();
        for (Byte b : in)
            builder.append(Convert.BYTE_2_STR.convert(b));
        return builder.toString();
    };
    public static final Convert<byte[] , String> BYTES_TO_HEX = (byte[] in) -> {
        StringBuilder builder = new StringBuilder();
        for (Byte b : in)
            builder.append(Convert.BYTE_2_STR.convert(b));
        return builder.toString();
    };
    public static final Convert<String , Byte[]> HEX_2_BYTES = new ConvertStr2Byte();
    public static final Convert<String , byte[]> HEX_TO_BYTES = (String in) -> ConvertStr2Byte.hexStringTobytes(in);
    public static final Convert<Object , BigDecimal> OB_2_BDECIMAL = in -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : new BigDecimal(str);
    };
    public static final Convert<Object , BigInteger> OB_2_BINT = in -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : new BigInteger(str);
    };
    public static final Convert<Object , Boolean> OB_2_BOOLEAN = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Boolean.valueOf(str);
    };
    public static final Convert<Object , Character> OB_2_CHAR = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str.charAt(0);
    };
    public static final Convert<Object , Double> OB_2_DOUBLE = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Double.valueOf(str);
    };
    public static final Convert<Object , Float> OB_2_FLOAT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Float.valueOf(str);
    };
    public static final Convert<Object , Integer> OB_2_INT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Integer.valueOf(str);
    };
    public static final Convert<Object , Long> OB_2_LONG = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Long.valueOf(str);
    };
    public static final Convert<Object , Object> OB_2_OB = (Object in) -> in;
    public static final Convert<Object , Short> OB_2_SHORT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Short.valueOf(str);
    };
    public static final Convert<Object , String> OB_2_STR = (Object in) -> Objects.toString(in , null);

    public static <IN , OUT> OUT converting(IN in , Convert<IN , OUT> convert) {
        return convert.convert(in);
    }

    OUT convert(IN in) throws NotSupportConvertException;
}
