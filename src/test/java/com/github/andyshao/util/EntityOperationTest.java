package com.github.andyshao.util;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.util.annotation.IgnoreCopy;

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
