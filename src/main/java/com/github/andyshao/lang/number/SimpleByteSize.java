package com.github.andyshao.lang.number;

import java.io.Serial;
import java.io.Serializable;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class SimpleByteSize implements ByteSize, Serializable, Comparable<SimpleByteSize> {
    @Serial
    private static final long serialVersionUID = 64729209707050872L;
    /**l*/
    private long l = 0L;
    /**{@link ByteLevel}*/
    private ByteLevel level = ByteLevel.MB;

    /**
     * build {@link SimpleByteSize}
     * @param l size number
     * @param level {@link ByteLevel}
     */
    public SimpleByteSize(long l, ByteLevel level) {
        this.l = l;
        this.level = level;
    }

    /**
     * No arg construction
     */
    public SimpleByteSize() {
    }

    @Override
    public void setSize(long l , ByteLevel level) {
        if(l < 0) throw new IllegalArgumentException();
        this.l = l;
        this.level = level;
    }

    @Override
    public long getSize(ByteLevel level) {
        int range = level.getLevel() - this.level.getLevel();
        if(range == 0) return this.getSize();
        else if(range > 0) {
            long tmp = this.l;
            for(int i=0; i<range; i++){
                tmp = tmp / RANGE_SIZE;
            }
            if(tmp <= 0) throw new ByteSizeOverLoadException();
            return tmp;
        } else {
            long tmp = this.l;
            range = -range;
            for(int i=0; i<range; i++) {
                tmp = tmp * RANGE_SIZE;
            }
            if(tmp <= 0) throw new ByteSizeOverLoadException();
            return tmp;
        }
    }

    @Override
    public long getSize() {
        return l;
    }

    @Override
    public ByteSize convert(String in) {
        return parse(in);
    }

    /**
     * parse from {@link String}
     * @param in {@link String}
     * @return {@link ByteSize}
     */
    public static ByteSize parse(String in) {
        ByteSize result = null;
        ByteLevel[] values = ByteLevel.values();
        for (ByteLevel byteLevel : values) {
            String tag = byteLevel.getTag();
            if(in.endsWith(tag) || in.endsWith(tag.toLowerCase())) {
                String tmp = in.substring(0 , in.length() - tag.length());
                boolean numCheck = tmp.matches("^[0-9]+$");
                if(numCheck) {
                    result = new SimpleByteSize();
                    result.setSize(Long.valueOf(tmp) , byteLevel);
                    break;
                } else continue;
            } else continue;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.l).append(this.level.getTag());
        return builder.toString();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (l ^ (l >>> 32));
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        SimpleByteSize other = (SimpleByteSize) obj;
        if (l != other.l) return false;
        if (level != other.level) return false;
        return true;
    }

    @Override
    public ByteLevel getLevel() {
        return this.level;
    }

    @Override
    public int compareTo(SimpleByteSize that) {
        if(that == null) return 1;
        ByteLevel minLevel = ByteLevel.min(that.getLevel() , this.getLevel());
        long thatSize = that.getSize(minLevel);
        long thisSize = this.getSize(minLevel);
        if(thisSize > thatSize) return 1;
        else if(thisSize < thatSize) return -1;
        else return 0;
    }

}
