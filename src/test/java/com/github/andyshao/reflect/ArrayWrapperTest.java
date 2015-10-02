package com.github.andyshao.reflect;

import org.junit.Test;

public class ArrayWrapperTest {

    @Test
    public void newInstance(){
        ArrayWrapper.newInstance(Integer[].class , 3);
        ArrayWrapper.newInstance(int[].class , 3);
    }
}
