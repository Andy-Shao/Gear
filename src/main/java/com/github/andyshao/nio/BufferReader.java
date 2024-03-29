package com.github.andyshao.nio;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

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

    /**
     * separate point
     */
    public static class SeparatePoint {
        private int nextStartSit;
        private int separatePoint;

        /**
         * build {@link SeparatePoint}
         * @param separatePoint point number
         */
        public SeparatePoint(int separatePoint) {
            this(separatePoint , separatePoint + 1);
        }

        /**
         * build {@link SeparatePoint}
         * @param separatePoint point number
         * @param nextStartSit next sit
         */
        public SeparatePoint(int separatePoint , int nextStartSit) {
            this.separatePoint = separatePoint;
            this.nextStartSit = nextStartSit;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof SeparatePoint) {
                SeparatePoint that = (SeparatePoint) obj;
                return Objects.equals(this.separatePoint , that.separatePoint) && Objects.equals(this.nextStartSit , that.nextStartSit);
            } else return false;
        }

        /**
         * get method
         * @return next start sit
         */
        public int getNextStartSit() {
            return this.nextStartSit;
        }

        /**
         * get method
         * @return separate point
         */
        public int getSeparatePoint() {
            return this.separatePoint;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.separatePoint , this.nextStartSit);
        }

        @Override
        public String toString() {
            return "SeparatePoint [nextStartSit=" + this.nextStartSit + ", separatePoint=" + this.separatePoint + "]";
        }
    }

    /**
     * reading status.
     * 
     * @return if the reading is finishing then return false
     */
    public boolean hasNext();

    /**
     * read information
     * 
     * @return the information
     * @throws IOException The mistake comes from IO operation
     */
    ARRAY read() throws IOException;
}
