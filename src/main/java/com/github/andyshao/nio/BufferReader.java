package com.github.andyshao.nio;

import java.io.Closeable;
import java.io.IOException;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jun 21, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <ARRAY> array type
 */
public interface BufferReader<ARRAY> extends Closeable {

    public static class SeparatePoint {
        private int nextStartSit;
        private int separatePoint;

        public SeparatePoint(int separatePoint , int nextStartSit) {
            this.separatePoint = separatePoint;
            this.nextStartSit = nextStartSit;
        }

        public int getNextStartSit() {
            return this.nextStartSit;
        }

        public int getSeparatePoint() {
            return this.separatePoint;
        }

        @Override
        public String toString() {
            return "SeparatePoint [nextStartSit=" + this.nextStartSit + ", separatePoint=" + this.separatePoint + "]";
        }
    }

    /**
     * read information
     * 
     * @return if reading is finished then return null
     * @throws IOException The mistake comes from IO operation
     */
    ARRAY read() throws IOException;
}
