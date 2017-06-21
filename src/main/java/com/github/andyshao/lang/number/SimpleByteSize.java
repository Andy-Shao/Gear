package com.github.andyshao.lang.number;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) 21 Jun 2017<br>
 * Encoding:UNIX UTF-8
 * @author Andy.Shao
 *
 */
public class SimpleByteSize implements ByteSize {
    private long l = 0L;
    private ByteLevel level = ByteLevel.MB;

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
        else if(range < 0) {
            long tmp = this.l;
            range = -range;
            for(int i=0; i<range; i++){
                tmp = tmp / RANGE_SIZE;
            }
            if(tmp < 0) throw new ByteSizeOverException();
            return tmp;
        } else {
            long tmp = this.l;
            for(int i=0; i<range; i++) {
                tmp = tmp * RANGE_SIZE;
            }
            if(tmp < 0) throw new ByteSizeOverException();
            return tmp;
        }
    }

    @Override
    public long getSize() {
        return l;
    }

}
