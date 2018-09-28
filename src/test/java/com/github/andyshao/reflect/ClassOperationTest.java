package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

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
        assertEquals(myInterface.doDefault() , ("doDefault"));
        assertEquals(myInterface.doInt() , (0));
        assertEquals(myInterface.doBoolean() , (false));
        assertTrue(myInterface.doString() == null);
        assertEquals(myInterface.doList("" , "") == null , (true));

        @SuppressWarnings("unchecked")
        MyGenericInterface<String> myGenericInterface = ClassOperation.newInstanceForInterface(MyGenericInterface.class , "andy.shao.MyGenericClass" , false , Version.V1_8);
        myGenericInterface.doVoid();
        assertEquals(myGenericInterface.doDefault() , ("doDefault"));
        assertEquals(myGenericInterface.doInt() , (0));
        assertEquals(myGenericInterface.doBoolean() , (false));
        assertTrue(myGenericInterface.doString() == null);
        assertTrue(myGenericInterface.doGeneric() == null);

        myInterface = ClassOperation.newInstanceForInterface(MyInterface.class , "andy.shao.MyClass2" , true , Version.V1_8);
        File file = new File("andy/shao/MyClass2.class");
        assertEquals(file.exists() , (true));
        file.delete();
        new File("andy/shao").delete();
        new File("andy").delete();
    }
}
