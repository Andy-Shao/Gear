package com.github.andyshao.data.structure;

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
    private final SingleLinked<D> linked = SingleLinked
        .defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data));

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
        this.linked.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.linked.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return this.linked.containsAll(c);
    }

    @Override
    public D element() {
        return this.peek();
    }

    @Override
    public boolean isEmpty() {
        return this.linked.isEmpty();
    }

    @Override
    public Iterator<D> iterator() {
        return this.linked.iterator();
    }

    @Override
    public boolean offer(D e) {
        return this.linked.add(e);
    }

    @Override
    public D peek() {
        if (this.size() == 0) return null;
        return this.linked.head().data();
    }

    @Override
    public D poll() {
        if (this.size() == 0) return null;
        return this.linked.remNext(null);
    }

    @Override
    public D remove() {
        return this.poll();
    }

    @Override
    public boolean remove(Object o) {
        return this.linked.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return this.linked.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return this.linked.retainAll(c);
    }

    @Override
    public int size() {
        return this.linked.size();
    }

    @Override
    public Object[] toArray() {
        return this.linked.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return this.linked.toArray(a);
    }

}
