package com.github.andyshao.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.util.annotation.IgnoreCopy;

import lombok.Data;

public class EntityOperationTest {

    @Test
    public void testCopyProperties() {
        MyClassOne one = new MyClassOne();
        one.setOne("one");
        one.setTwo("two");
        MyClassTwo two = new MyClassTwo();
        EntityOperation.copyProperties(one , two);
        
        Assert.assertThat(two.getOne() , Matchers.is(one.getOne()));
        Assert.assertNull(two.getTwo());
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
    
    static class MyClassOne {
        private String one;
        @IgnoreCopy
        private String two;
        public String getOne() {
            return one;
        }
        public void setOne(String one) {
            this.one = one;
        }
        public String getTwo() {
            return two;
        }
        public void setTwo(String two) {
            this.two = two;
        }
    }
    
    static class MyClassTwo {
        private String one;
        private String two;
        public String getOne() {
            return one;
        }
        public void setOne(String one) {
            this.one = one;
        }
        public String getTwo() {
            return two;
        }
        public void setTwo(String two) {
            this.two = two;
        }
    }
}
