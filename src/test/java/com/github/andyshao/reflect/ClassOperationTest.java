package com.github.andyshao.reflect;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.asm.Version;

public class ClassOperationTest {
    public static interface MyGenericInterface<T> {
        boolean doBoolean();

        default String doDefault() {
            return "doDefault";
        }

        T doGeneric();

        int doInt();

        String doString();

        void doVoid();
    }

    public static interface MyInterface {
        boolean doBoolean();

        default String doDefault() {
            return "doDefault";
        }

        int doInt();

        List<Integer> doList(String strOne , String strTwo);

        String doString();

        void doVoid();
    }

    @Test
    public void testNewInstanceForInterface() throws IOException {
        MyInterface myInterface = ClassOperation.newInstanceForInterface(ClassOperationTest.MyInterface.class , "andy.shao.MyClass" , false , Version.V1_8);
        myInterface.doVoid();
        Assert.assertThat(myInterface.doDefault() , Matchers.is("doDefault"));
        Assert.assertThat(myInterface.doInt() , Matchers.is(0));
        Assert.assertThat(myInterface.doBoolean() , Matchers.is(false));
        Assert.assertTrue(myInterface.doString() == null);
        Assert.assertThat(myInterface.doList("" , "") == null , Matchers.is(true));

        @SuppressWarnings("unchecked")
        MyGenericInterface<String> myGenericInterface = ClassOperation.newInstanceForInterface(MyGenericInterface.class , "andy.shao.MyGenericClass" , false , Version.V1_8);
        myGenericInterface.doVoid();
        Assert.assertThat(myGenericInterface.doDefault() , Matchers.is("doDefault"));
        Assert.assertThat(myGenericInterface.doInt() , Matchers.is(0));
        Assert.assertThat(myGenericInterface.doBoolean() , Matchers.is(false));
        Assert.assertTrue(myGenericInterface.doString() == null);
        Assert.assertTrue(myGenericInterface.doGeneric() == null);

        myInterface = ClassOperation.newInstanceForInterface(MyInterface.class , "andy.shao.MyClass2" , true , Version.V1_8);
        File file = new File("andy/shao/MyClass2.class");
        Assert.assertThat(file.exists() , Matchers.is(true));
        file.delete();
        new File("andy/shao").delete();
        new File("andy").delete();
    }
}