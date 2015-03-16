package com.github.andyshao.test.data.structure.convert;

import java.util.Iterator;
import java.util.Set;

import com.github.andyshao.lang.Convert;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 14, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
public class SetConvert<D> implements Convert<Set<D> , com.github.andyshao.test.data.structure.Set<D>> {

    @Override
    public com.github.andyshao.test.data.structure.Set<D> convert(final Set<D> in) {
        return new com.github.andyshao.test.data.structure.Set<D>() {
            @Override
            public void clear() {
                in.clear();
            }

            @Override
            public Iterator<D> iterator() {
                return in.iterator();
            }

            @Override
            public void set_insert(D data) {
                in.add(data);
            }

            @Override
            public boolean set_is_member(D data) {
                return in.contains(data);
            }

            @Override
            public boolean set_is_subset(com.github.andyshao.test.data.structure.Set<D> set1) {
                for (D data : in)
                    if (!set1.set_is_member(data)) return false;

                return true;
            }

            @Override
            public void set_remove(D data) {
                in.remove(data);
            }

            @Override
            public int size() {
                return in.size();
            }
        };
    }
}
