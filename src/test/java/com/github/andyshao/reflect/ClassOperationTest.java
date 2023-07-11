package com.github.andyshao.reflect;

import com.github.andyshao.asm.Version;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
    
    public static class MyGenericClass implements MyGenericInterface<String> {

		@Override
		public boolean doBoolean() {
			return false;
		}

		@Override
		public String doGeneric() {
			return null;
		}

		@Override
		public int doInt() {
			return 0;
		}

		@Override
		public String doString() {
			return null;
		}

		@Override
		public void doVoid() {
		}
    	
    }
    
    public static class GenericClass<T> {
    	public T doGeneric() {
    		return null;
    	}
    }
    
    public static class ChildClass<E> extends GenericClass<Long> {
    }
    
    public static class MyChildClass extends FileInputStream {

		public MyChildClass(File file) throws FileNotFoundException {
			super(file);
		}
	}
    
    public static class ChildClass2 extends GenericClass<Map<String, List<Integer>>> {}
    
    public static class ChildClass3<E> extends GenericClass<E> {}

    @Test
    public void testNewInstanceForInterface() throws IOException {
        MyInterface myInterface = ClassOperation.newInstanceForInterface(ClassOperationTest.MyInterface.class , "andy.shao.MyClass" , false , Version.V17);
        myInterface.doVoid();
        assertEquals(myInterface.doDefault() , ("doDefault"));
        assertEquals(myInterface.doInt() , (0));
        assertEquals(myInterface.doBoolean() , (false));
        assertNull(myInterface.doString());
        assertEquals(myInterface.doList("" , "") == null , (true));

        @SuppressWarnings("unchecked")
        MyGenericInterface<String> myGenericInterface = ClassOperation.newInstanceForInterface(MyGenericInterface.class , "andy.shao.MyGenericClass" , false , Version.V17);
        myGenericInterface.doVoid();
        assertEquals(myGenericInterface.doDefault() , ("doDefault"));
        assertEquals(myGenericInterface.doInt() , (0));
        assertEquals(myGenericInterface.doBoolean() , (false));
        assertNull(myGenericInterface.doString());
        assertNull(myGenericInterface.doGeneric());

        myInterface = ClassOperation.newInstanceForInterface(MyInterface.class , "andy.shao.MyClass2" , true , Version.V17);
        File file = new File("andy/shao/MyClass2.class");
        assertEquals(file.exists() , (true));
        file.delete();
        new File("andy/shao").delete();
        new File("andy").delete();
    }
    
    @Test
    public void testGetSuperClasssGenericInfo() {
    	Type type = Object.class.getGenericSuperclass();
    	assertNull(type);
    	
    	GenericNode info = ClassOperation.getSuperClassGenericInfo(MyGenericInterface.class);
    	assertNull(info);
    	
    	info = ClassOperation.getSuperClassGenericInfo(MyGenericClass.class);
    	assertNotNull(info);
    	assertFalse(info.isGeneiric());
    	
    	info = ClassOperation.getSuperClassGenericInfo(MyChildClass.class);
    	assertNotNull(info);
    	assertFalse(info.isGeneiric());
    	
    	info = ClassOperation.getSuperClassGenericInfo(ChildClass.class);
    	assertNotNull(info);
    	assertTrue(info.isGeneiric());
    	
    	info = ClassOperation.getSuperClassGenericInfo(ChildClass2.class);
    	assertNotNull(info);
    	assertTrue(info.isGeneiric());
    	
    	info = ClassOperation.getSuperClassGenericInfo(ChildClass3.class);
    	assertNotNull(info);
    	assertTrue(info.isGeneiric());
    }
}
