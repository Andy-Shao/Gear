package com.github.andyshao.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

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
        
        Assert.assertThat(two.getOne() , Matchers.is(one.getOne()));
        Assert.assertNull(two.getTwo());
        Assert.assertNull(two.getThree());
        Assert.assertThat(two.getFour() , Matchers.is(4));
        Assert.assertThat(two.getFive() , Matchers.is(5L));
    }
    
    @Test
    public void testCopyArrayPros() {
        ArrayClassOne one = new ArrayClassOne();
        one.setIp("localhost");
        one.setIps(new String[] {"localhost", "127.0.0.1"});
        one.setMachines("machine01");
        ArrayClassTwo two = new ArrayClassTwo();
        EntityOperation.copyProperties(one , two);
        Assert.assertThat(two.getIp() , Matchers.is("localhost"));
        Assert.assertNull(two.getIps());
        Assert.assertNull(two.getMachines());
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
        
        Assert.assertThat(child.getThree() , Matchers.is(item.getThree()));
        Assert.assertThat(child.getFour() , Matchers.is(item.getFour()));
        Assert.assertThat(child.getOne() , Matchers.is(item.getOne()));
        Assert.assertThat(child.getTwo() , Matchers.is(item.getTwo()));
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
