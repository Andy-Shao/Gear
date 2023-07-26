package com.github.andyshao.lang;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    /**byte to string*/
    public static final Convert<Byte , String> BYTE_2_STR = ConvertByte2Str.byte2Char(ConvertByte2Str.BYTE_HEX);
    /**bytes to hex*/
    public static final Convert<Byte[] , String> BYTES_2_HEX = (Byte[] in) -> {
        StringBuilder builder = new StringBuilder();
        for (Byte b : in)
            builder.append(Convert.BYTE_2_STR.convert(b));
        return builder.toString();
    };
    /**bytes to hex*/
    public static final Convert<byte[] , String> BYTES_TO_HEX = (byte[] in) -> {
        StringBuilder builder = new StringBuilder();
        for (Byte b : in)
            builder.append(Convert.BYTE_2_STR.convert(b));
        return builder.toString();
    };
    /**hex to bytes*/
    public static final Convert<String , Byte[]> HEX_2_BYTES = new ConvertStr2Byte();
    /**hex to bytes*/
    public static final Convert<String , byte[]> HEX_TO_BYTES = (String in) -> ConvertStr2Byte.hexStringTobytes(in);
    /**object to {@link BigDecimal}*/
    public static final Convert<Object , BigDecimal> OB_2_BDECIMAL = in -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : new BigDecimal(str);
    };
    /**object to {@link BigInteger}*/
    public static final Convert<Object , BigInteger> OB_2_BINT = in -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : new BigInteger(str);
    };
    /**object to {@link Boolean}*/
    public static final Convert<Object , Boolean> OB_2_BOOLEAN = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Boolean.valueOf(str);
    };
    /**object to {@link Character}*/
    public static final Convert<Object , Character> OB_2_CHAR = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str.charAt(0);
    };
    /**object to {@link Double}*/
    public static final Convert<Object , Double> OB_2_DOUBLE = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Double.valueOf(str);
    };
    /**object to {@link Float}*/
    public static final Convert<Object , Float> OB_2_FLOAT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Float.valueOf(str);
    };
    /**object to {@link Integer}*/
    public static final Convert<Object , Integer> OB_2_INT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Integer.valueOf(str);
    };
    /**object to {@link Long}*/
    public static final Convert<Object , Long> OB_2_LONG = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Long.valueOf(str);
    };
    /**object to object*/
    public static final Convert<Object , Object> OB_2_OB = (Object in) -> in;
    /**object to {@link Short}*/
    public static final Convert<Object , Short> OB_2_SHORT = (Object in) -> {
        String str = Convert.OB_2_STR.convert(in);
        return str == null ? null : Short.valueOf(str);
    };
    /**object to {@link String}*/
    public static final Convert<Object , String> OB_2_STR = (Object in) -> Objects.toString(in , null);

    /**
     * converting
     * @param in input
     * @param convert covert
     * @return output
     * @param <IN> input type
     * @param <OUT> output type
     */
    public static <IN , OUT> OUT converting(IN in , Convert<IN , OUT> convert) {
        return convert.convert(in);
    }

    /**
     * identity convert
     * @return return origin object
     * @param <E> data type
     */
    public static <E> Convert<E, E> identity() {
    	return in -> in;
    }

    /**
     * covert action
     * @param in input
     * @return output
     * @throws NotSupportConvertException cannot do the convert
     */
    OUT convert(IN in) throws NotSupportConvertException;

    /**
     * fill operation
     * @param condition conidtion
     * @param getOp get method
     * @param setOp set method
     * @param <E> data type
     */
    public static <E> void fill(boolean condition, Supplier<E> getOp, Consumer<E> setOp) {
        if(condition) {
            setOp.accept(getOp.get());
        }
    }

    /**
     * fill data by condition
     * @param condition condition
     * @param getOp get method
     * @param setOp set method
     * @param <E> data type
     */
    public static <E> void fill(Function<E, Boolean> condition, Supplier<E> getOp, Consumer<E> setOp) {
        E val = getOp.get();
        if(condition.apply(val)) {
            setOp.accept(val);
        }
    }

    /**
     * fill data by not null condition
     * @param getOp get method
     * @param setOp set method
     * @param <E> data type
     */
    public static <E> void fill(Supplier<E> getOp, Consumer<E> setOp) {
        fill(Objects::nonNull, getOp, setOp);
    }
}
