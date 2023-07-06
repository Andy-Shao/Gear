package com.github.andyshao.lang;

/**
 * 
 * Descript:<br>
 * Copyright: Copyright(c) Aug 7, 2014<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public class ConvertByte2Str implements Convert<Byte , String> {

    /**Byte hex*/
    public static final String[] BYTE_HEX = new String[] { "0" , "1" , "2" , "3" , "4" , "5" , "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" };

    /**
     * byte to char
     * @param format like {@link ConvertByte2Str#BYTE_HEX}
     * @return output
     */
    public static Convert<Byte , String> byte2Char(final String[] format) {
        return (Byte in) -> {
            int head = (in & 0xF0) >> 4;
            int tail = in & 0x0F;
            return format[head] + format[tail];
        };
    }

    /**
     * byte to {@link String}
     * @param format format string like {@link ConvertByte2Str#BYTE_HEX}
     * @return
     */
    public static Convert<Byte[] , String> byte2Str(final String[] format) {
        return (Byte[] in) -> {
            Convert<Byte , String> byte2char = ConvertByte2Str.byte2Char(format);
            StringBuilder stringBuilder = new StringBuilder();
            for (Byte b : in)
                stringBuilder.append(byte2char.convert(b));

            return stringBuilder.toString();
        };
    }

    private volatile String[] byte2Char;

    private volatile Convert<Byte , String> proxy;

    @Override
    public String convert(Byte in) {
        return this.proxy.convert(in);
    }

    /**
     * set byte 2 char
     * @param byte2Char like {@link ConvertByte2Str#BYTE_HEX}
     */
    public void setByte2Char(String[] byte2Char) {
        this.byte2Char = byte2Char;
        this.proxy = ConvertByte2Str.byte2Char(this.byte2Char);
    }
}
