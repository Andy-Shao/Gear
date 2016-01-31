package com.github.andyshao.reflect;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class ClassOperationTest {
    static interface MyInterface{
        void doVoid();
        boolean doBoolean();
        int doInt();
        default String doDefault(){
            return "doDefault";
        }
    }
    
    @Test
    public void testNewInterfaceInstance(){
        MyInterface myInterface = ClassOperation.newInterfaceInstance(MyInterface.class);
        myInterface.doVoid();
        Assert.assertThat(myInterface.doBoolean() , Matchers.is(false));
        Assert.assertThat(myInterface.doInt() , Matchers.is(0));
        System.out.println(myInterface.doDefault());
    }
}
