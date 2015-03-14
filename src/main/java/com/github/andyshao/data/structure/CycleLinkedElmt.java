package com.github.andyshao.data.structure;

import java.util.Objects;

public interface CycleLinkedElmt<DATA> extends Linked.LinkedElmt<DATA , CycleLinkedElmt<DATA>> {
    public static <DAT> CycleLinkedElmt<DAT> defaultElmt() {
        CycleLinkedElmt<DAT> result = new CycleLinkedElmt<DAT>() {
            private DAT data;
            private CycleLinkedElmt<DAT> next;

            @Override
            public DAT data() {
                return this.data;
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean equals(Object obj) {
                CycleLinkedElmt<DAT> that;
                if (obj instanceof CycleLinkedElmt) {
                    that = (CycleLinkedElmt<DAT>) obj;
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
            public CycleLinkedElmt<DAT> next() {
                return this.next;
            }

            @Override
            public void setData(DAT data) {
                this.data = data;
            }

            @Override
            public void setNext(CycleLinkedElmt<DAT> next) {
                this.next = next;
            }
        };
        return result;
    }

    public static <DAT> CycleLinkedElmt<DAT> defaultElmt(DAT data) {
        CycleLinkedElmt<DAT> result = CycleLinkedElmt.defaultElmt();
        result.setData(data);
        return result;
    }
}