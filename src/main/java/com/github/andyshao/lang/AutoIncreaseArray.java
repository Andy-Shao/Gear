package com.github.andyshao.lang;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

import com.github.andyshao.util.ArrayTools;

public class AutoIncreaseArray<D> implements Iterable<D> , Cleanable {

    private long actionAccount;
    private D[] array;
    private int arraySize;
    private int end;
    private int size;
    private int start;

    public AutoIncreaseArray() {
        this(16);
    }

    public AutoIncreaseArray(int arraySize) {
        this.arraySize = arraySize;
        this.end = this.arraySize >> 1;
        this.start = this.end;
        this.actionAccount = 0;
        this.size = 0;
    }

    @SuppressWarnings("unchecked")
    public void addHead(D data) {
        if (data == null) throw new NullPointerException();
        if (this.array == null) this.array = (D[]) Array.newInstance(data.getClass() , this.arraySize);

        //Increase the array space
        if (this.start == 0) this.replaceSpace(data.getClass());

        if (this.size == 0) this.array[this.start] = data;
        else this.array[--this.start] = data;
        this.actionAccount++;
        this.size++;
    }

    @SuppressWarnings("unchecked")
    public void addTail(D data) {
        if (data == null) throw new NullPointerException();
        if (this.array == null) this.array = (D[]) Array.newInstance(data.getClass() , this.arraySize);

        //Increase the array space
        if (this.end == this.arraySize - 1) this.replaceSpace(data.getClass());

        if (this.size == 0) this.array[this.end] = data;
        else this.array[++this.end] = data;
        this.actionAccount++;
        this.size++;
    }

    @Override
    public void clean() {
        this.actionAccount++;
        this.end = this.arraySize >> 1;
        this.start = this.end;
        this.array = null;
        this.size = 0;
    }

    public D get(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        return this.array[index + this.start];
    };

    @Override
    public Iterator<D> iterator() {
        return new Iterator<D>() {
            private final long actionAcount = AutoIncreaseArray.this.actionAccount;
            private int index = 0;

            @Override
            public boolean hasNext() {
                if (this.actionAcount != AutoIncreaseArray.this.actionAccount) throw new ConcurrentModificationException();
                return this.index < AutoIncreaseArray.this.size;
            }

            @Override
            public D next() {
                return AutoIncreaseArray.this.array[(this.index++) + AutoIncreaseArray.this.start];
            }
        };
    }

    public int newSize(int size) {
        long result = ((long) size) << 1;
        return result - (Integer.MAX_VALUE) > 0 ? Integer.MAX_VALUE : (int) result;
    }

    public D remove(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        index = index + this.start;
        D result = this.array[index];
        this.array = ArrayTools.removeItem(this.array , index);
        this.actionAccount++;
        this.size--;
        return result;
    }

    @SuppressWarnings("unchecked")
    private void replaceSpace(Class<? extends Object> data_type) {
        int arraySize = this.newSize(this.array.length);
        D[] temp = (D[]) Array.newInstance(data_type , arraySize);
        int new_start = arraySize >> 2;
        System.arraycopy(this.array , this.start , temp , new_start , this.size);
        this.array = temp;
        this.arraySize = arraySize;
        this.start = new_start;
        this.end = this.start + this.size - 1;
    }

    public D set(D data , int index) {
        D result = null;
        if (data == null) throw new NullPointerException();
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        index = index + this.start;
        result = this.array[index];
        this.array[index] = data;
        this.actionAccount++;
        return result;
    }

    public int size() {
        return this.size;
    }
}
