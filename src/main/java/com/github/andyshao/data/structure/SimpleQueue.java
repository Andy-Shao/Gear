package com.github.andyshao.data.structure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 26, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public class SimpleQueue<D> implements Queue<D> {
    private final ArrayList<D> list = new ArrayList<>();

    @Override
    public boolean add(D e) {
        return this.offer(e);
    }

    @Override
    public boolean addAll(Collection<? extends D> c) {
        boolean result = true;
        for (D d : c)
            result |= this.offer(d);
        return result;
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.list.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.list.containsAll(c);
    }

    @Override
    public D element() {
        return this.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public Iterator<D> iterator() {
        return this.list.iterator();
    }

    @Override
    public boolean offer(D e) {
        return this.list.add(e);
    }

    @Override
    public D peek() {
        if (this.size() == 0) return null;
        return this.list.get(0);
    }

    @Override
    public D poll() {
        if (this.size() == 0) return null;
        return this.list.remove(0);
    }

    @Override
    public D remove() {
        return this.poll();
    }

    @Override
    public boolean remove(Object o) {
        return this.list.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.list.retainAll(c);
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public Object[] toArray() {
        return this.list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.list.toArray(a);
    }

}
