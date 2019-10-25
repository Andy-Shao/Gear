package com.github.andyshao.lang;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Objects;

import com.github.andyshao.reflect.ArrayOperation;
import com.github.andyshao.util.CollectionModel;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 17, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public class AutoIncreaseArray<D> implements CollectionModel<D> , Cleanable {
    public static final <E> AutoIncreaseArray<E> toAutoIncreaseArray(E[] array) {
        AutoIncreaseArray<E> autoIncreaseArray = new AutoIncreaseArray<E>();
        autoIncreaseArray.array = array;
        autoIncreaseArray.arraySize = array.length;
        autoIncreaseArray.size = array.length;
        autoIncreaseArray.start = 0;
        autoIncreaseArray.end = array.length - 1;
        return autoIncreaseArray;
    }

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

    @Override
    public boolean add(D e) {
        AutoIncreaseArray.this.addTail(e);
        return true;
    }

    /**
     * add the data in the head.
     * 
     * @param data information
     * @return the index which storage the data.
     */
    @SuppressWarnings("unchecked")
    public int addHead(D data) {
        if (data == null) throw new NullPointerException();
        if (this.array == null) this.array = (D[]) Array.newInstance(data.getClass() , this.arraySize);

        //Increase the array space
        if (this.start == 0) this.replaceSpace(data.getClass());

        if (this.size == 0) this.array[this.start] = data;
        else this.array[--this.start] = data;
        this.actionAccount++;
        this.size++;
        return this.size - 1;
    }

    /**
     * add the data in the tail.
     * 
     * @param data information
     * @return the index which storage the data.
     */
    @SuppressWarnings("unchecked")
    public int addTail(D data) {
        if (data == null) throw new NullPointerException();
        if (this.array == null) this.array = (D[]) Array.newInstance(data.getClass() , this.arraySize);

        //Increase the array space
        if (this.end == this.arraySize - 1) this.replaceSpace(data.getClass());

        if (this.size == 0) this.array[this.end] = data;
        else this.array[++this.end] = data;
        this.actionAccount++;
        this.size++;
        return this.size - 1;
    }

    @Override
    public void clear() {
        this.actionAccount++;
        this.end = this.arraySize >> 1;
        this.start = this.end;
        this.array = null;
        this.size = 0;
    };

    public D get(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        return this.array[index + this.start];
    }

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
        return (int) Math.min(result, Integer.MAX_VALUE);
    }

    public D remove(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        index = index + this.start;
        D result = this.array[index];
        this.array = ArrayOperation.removeItem(this.array , index);
        this.actionAccount++;
        this.size--;
        return result;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0 ; i < this.size() ; i++)
            if (Objects.equals(o , AutoIncreaseArray.this.get(i))) {
                AutoIncreaseArray.this.remove(i);
                break;
            }
        return true;
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

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i = 0 ; i < this.size() ; i++)
            if (!c.contains(AutoIncreaseArray.this.get(i))) AutoIncreaseArray.this.remove(i);
        return true;
    }

    public D set(D data , int index) {
        D result = null;
        if (data == null) throw new NullPointerException();
        if (index < 0) throw new IndexOutOfBoundsException();
        else if (index >= this.size) {
            this.replaceSpace(data.getClass());
            this.set(data , index);
        }
        index = index + this.start;
        result = this.array[index];
        this.array[index] = data;
        this.actionAccount++;
        return result;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[this.size()];
        System.arraycopy(AutoIncreaseArray.this.array , this.start , array , 0 , array.length);
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        T[] result = null;
        if (a.length >= this.size()) result = a;
        else result = (T[]) Array.newInstance(a.getClass().getComponentType() , this.size());

        System.arraycopy(AutoIncreaseArray.this.array , this.start , result , 0 , result.length);
        return result;
    }
    
    @SuppressWarnings("unchecked")
	public void addAll(D[] a) {
    	int newSize = a.length + this.size;
    	int newArraySize = this.newSize(newSize);
    	int newStart = 0;
        int newEnd = newSize - 1;
    	D[] newArray = (D[]) Array.newInstance(a.getClass().getComponentType(), newArraySize);
    	System.arraycopy(this.array, this.start, newArray, 0, this.size);
    	System.arraycopy(a, 0, newArray, this.size, a.length);
    	this.actionAccount++;
    	this.array = newArray;
    	this.arraySize = newArraySize;
    	this.size = newSize;
    	this.start = newStart;
    	this.end = newEnd;
    }
}
