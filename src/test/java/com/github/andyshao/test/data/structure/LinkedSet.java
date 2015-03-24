package com.github.andyshao.test.data.structure;

import java.util.Iterator;
import java.util.function.Function;

import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.Linked;
import com.github.andyshao.data.structure.SingleLinked;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Feb 11, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 * @param <D> data
 */
@Deprecated
public interface LinkedSet<D> extends Linked<D , CycleLinkedElmt<D>> , Set<D> {

    public static <DATA> LinkedSet<DATA> DEFAULT_SET(final SingleLinked<DATA> linked) {
        return new LinkedSet<DATA>() {
            @Override
            public void clear() {
                linked.clear();
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean equals(Object obj) {
                LinkedSet<DATA> that;
                if (obj instanceof LinkedSet) {
                    that = (LinkedSet<DATA>) obj;
                    if (this.size() != that.size()) return false;

                    //Sets of the same size are equal if they are subsets.
                    return this.set_is_subset(that);
                } else return false;
            }

            @Override
            public Function<DATA , CycleLinkedElmt<DATA>> getElmtFactory() {
                return linked.getElmtFactory();
            }

            @Override
            public int hashCode() {
                int result = 0;
                for (CycleLinkedElmt<DATA> member = this.head() ; member != null ; member = member.next())
                    result += member.hashCode();
                return result;
            }

            @Override
            public CycleLinkedElmt<DATA> head() {
                return linked.head();
            }

            @Override
            public void insNext(CycleLinkedElmt<DATA> element , DATA data) {
                linked.insNext(element , data);
            }

            @Override
            public Iterator<DATA> iterator() {
                return linked.iterator();
            }

            @Override
            public DATA remNext(CycleLinkedElmt<DATA> element) {
                return linked.remNext(element);
            }

            @Override
            public void set_insert(DATA data) {
                //Do not allow the insertion of duplicates.
                if (this.set_is_member(data)) return;

                //Insert the data.
                linked.insNext(linked.tail() , data);
            }

            @Override
            public boolean set_is_member(DATA data) {
                CycleLinkedElmt<DATA> member;

                //Determine if the data is a member of the set.
                for (member = this.head() ; member != null ; member = member.next())
                    if (member.data().equals(data)) return true;

                return false;
            }

            @Override
            public boolean set_is_subset(Set<DATA> set1) {
                CycleLinkedElmt<DATA> member;

                //Do a quick test to rule out some cases.
                if (this.size() > set1.size()) return false;

                //Determine if set1 is a subset of set2.
                for (member = this.head() ; member != null ; member = member.next())
                    if (!set1.set_is_member(member.data())) return false;
                return true;
            }

            @Override
            public void set_remove(DATA data) {
                CycleLinkedElmt<DATA> member , prev;

                //Find the member to remove.
                prev = null;

                for (member = linked.head() ; member != null ; member = member.next()) {
                    if (member.data().equals(data)) break;
                    prev = member;
                }

                //Return if the member was not found.
                if (member == null) return;

                //Remove the member
                linked.remNext(prev);
            }

            @Override
            public int size() {
                return linked.size();
            }

            @Override
            public CycleLinkedElmt<DATA> tail() {
                return linked.tail();
            }
        };
    }
}
