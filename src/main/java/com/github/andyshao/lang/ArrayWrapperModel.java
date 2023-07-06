package com.github.andyshao.lang;

import java.io.Serial;
import java.util.Objects;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Oct 2, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public abstract class ArrayWrapperModel implements ArrayWrapper {
    @Serial
    private static final long serialVersionUID = 3637722120786952465L;
    /**the capacity of the {@link ArrayWrapperModel}*/
    protected int limit;
    /**the mark of the {@link ArrayWrapperModel}*/
    protected int mark;
    /**the current position of {@link ArrayWrapperModel}*/
    protected int position;

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ArrayWrapperModel) {
            ArrayWrapperModel that = (ArrayWrapperModel) obj;
            return Objects.equals(this.array() , that.array());
        } else return false;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Object element : this)
            result = 31 * result + (element == null ? 0 : element.hashCode());
        return result;
    }

    @Override
    public int limit() {
        return this.limit;
    }

    @Override
    public void limit(int limit) {
        if (limit < this.position() || limit < 0) throw new ArrayIndexOutOfBoundsException();
        this.limit = limit;
    }

    @Override
    public void mark() {
        this.mark = this.position();
    }

    @Override
    public void markLimit() {
        this.mark = this.limit();
    }

    @Override
    public int position() {
        return this.position;
    }

    @Override
    public void position(int position) {
        if (position < 0 || position > this.limit()) throw new ArrayIndexOutOfBoundsException();
        this.position = position;
    }

    /**
     * calculate real index
     * @param index index value
     * @return real index
     */
    protected int calculateRealIndex(int index) {
    	int realIndex = index + this.position();
    	if(realIndex >= this.limit() || realIndex < 0) throw new ArrayIndexOutOfBoundsException();
		return realIndex;
    }

    @Override
    public void reset() {
        this.position(this.mark);
    }

    @Override
    public void resetLimit() {
        this.limit(this.mark);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");
        for (Object obj : this)
            result.append(obj.toString()).append(", ");
        result.delete(result.length() - 2 , result.length());
        result.append("]");
        return result.toString();
    }
}
