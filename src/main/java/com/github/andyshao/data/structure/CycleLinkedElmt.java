package com.github.andyshao.data.structure;

import java.io.Serial;
import java.util.Objects;

/**
 * Cycle Linked Element
 * @param <DATA> data type
 */
public class CycleLinkedElmt<DATA> implements Linked.LinkedElmt<DATA , CycleLinkedElmt<DATA>> {
    @Serial
    private static final long serialVersionUID = -5760089081060466955L;

    /**
     * Build {@link CycleLinked}
     * @return {@link CycleLinked}
     * @param <DAT> data type
     */
    public static <DAT> CycleLinkedElmt<DAT> defaultElmt() {
        return new CycleLinkedElmt<DAT>();
    }

    /**
     * Build {@link CycleLinked}
     * @param data data
     * @return {@link CycleLinked}
     * @param <DAT> data type
     */
    public static <DAT> CycleLinkedElmt<DAT> defaultElmt(DAT data) {
        CycleLinkedElmt<DAT> result = CycleLinkedElmt.defaultElmt();
        result.setData(data);
        return result;
    }

    private DATA data;
    private CycleLinkedElmt<DATA> next;

    @Override
    public DATA data() {
        return this.data;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        CycleLinkedElmt<DATA> that;
        if (obj instanceof CycleLinkedElmt) {
            that = (CycleLinkedElmt<DATA>) obj;
            if (this.next == this) return Objects.equals(this.data , that.data());
            else return Objects.equals(this.data , that.data()) && Objects.equals(this.next , that.next());
        } else return false;
    }

    @Override
    public int hashCode() {
        if (this.next == this) return this.data.hashCode();
        else return Objects.hash(this.data , this.next);
    }

    @Override
    public CycleLinkedElmt<DATA> next() {
        return this.next;
    }

    @Override
    public void setData(DATA data) {
        this.data = data;
    }

    @Override
    public void setNext(CycleLinkedElmt<DATA> next) {
        this.next = next;
    }
}