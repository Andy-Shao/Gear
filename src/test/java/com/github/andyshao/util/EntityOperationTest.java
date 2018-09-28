package com.github.andyshao.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.github.andyshao.util.annotation.IgnoreCopy;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class EntityOperationTest {

    @Test
    public void testCopyProperties() {
        MyClassOne one = new MyClassOne();
        one.setOne("one");
        one.setTwo("two");
        one.setThree("three");
        one.setFour(4);
        one.setFive(5);
        MyClassTwo two = new MyClassTwo();
        EntityOperation.copyProperties(one , two);
        
        assertEquals(two.getOne() , (one.getOne()));
        assertNull(two.getTwo());
        assertNull(two.getThree());
        assertEquals(two.getFour() , (Integer)4);
        assertEquals(two.getFive() , (5L));
    }
    
    @Test
    public void testCopyArrayPros() {
        ArrayClassOne one = new ArrayClassOne();
        one.setIp("localhost");
        one.setIps(new String[] {"localhost", "127.0.0.1"});
        one.setMachines("machine01");
        ArrayClassTwo two = new ArrayClassTwo();
        EntityOperation.copyProperties(one , two);
        assertEquals(two.getIp() , ("localhost"));
        assertNull(two.getIps());
        assertNull(two.getMachines());
    }
    
    @Test
    public void testCopyParent() {
        Child child = new Child();
        child.setFour(4);
        child.setOne("one");
        child.setThree(3);
        child.setTwo("two");
        MyClassTwo item = new MyClassTwo();
        
        EntityOperation.copyProperties(child , item);
        
        assertEquals(child.getThree() , (item.getThree()));
        assertEquals(child.getFour() , (item.getFour()));
        assertEquals(child.getOne() , (item.getOne()));
        assertEquals(child.getTwo() , (item.getTwo()));
    }

    @Data
    static class ArrayClassOne {
        private String[] ips;
        private String ip;
        private String machines;
    }
    
    @Data
    static class ArrayClassTwo {
        private String ips;
        private String ip;
        private String[] machines;
    }
    
    @Data
    static class MyClassOne {
        private String one;
        @IgnoreCopy
        private String two;
        private String three;
        private int four;
        private int five;
    }
    
    @Data
    static class MyClassTwo {
        private String one;
        private String two;
        private Integer three;
        private Integer four;
        private long five;
    }
    
    @Data
    static class Parent {
        private String one;
        private String two;
    }
    
    @Data
    @EqualsAndHashCode(callSuper = true)
    static class Child extends Parent{
        private Integer three;
        private Integer four;
    }
}
