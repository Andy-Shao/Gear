package com.github.andyshao.reflect;

import org.junit.Test;

import com.github.andyshao.lang.ArrayWrapper;

public class ArrayWrapperTest {

    @Test
    public void newInstance(){
        ArrayWrapper.newInstance(Integer[].class , 3);
        ArrayWrapper.newInstance(int[].class , 3);
    }
}
