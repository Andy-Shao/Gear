package com.github.andyshao.data.structure;

import com.github.andyshao.lang.Cleanable;

public interface Tree<D,N extends Tree.TreeNode<D>> extends Cleanable {
    public interface TreeNode<DATA> {
        public DATA data();
        public void data(DATA data);
    }

    @Override
    public abstract void clear();
    public abstract N root();
    public abstract int size();

}