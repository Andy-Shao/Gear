package com.github.andyshao.data.structure;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class StackTest {
    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { { Stack.<String , CycleLinkedElmt<String>> defaultStack(SingleLinked.defaultSingleLinked(CycleLinkedElmt::defaultElmt)) } ,
            { Stack.<String , CycleLinkedElmt<String>> defaultStack(CycleLinked.defaultCycleLinked(CycleLinkedElmt::defaultElmt)) } });
    }

    private final String[] data = { "Andy" , "Shao" };

    private volatile Stack<String> stack;

    public StackTest(Stack<String> stack) {
        this.stack = stack;
    }
    
    @Test
    public void testEmptyStack() {
        Stack<String> emptyStack = Stack.defaultStack();
        for(String item : emptyStack) {
            item.toString();
            Assert.fail();
        }
    }

    @Test
    public void testIterator() {
        for (int i = 0 ; i < this.data.length ; i++)
            this.stack.push(this.data[i]);

        Iterator<String> iterator = this.stack.iterator();
        Assert.assertThat(iterator.next() , Matchers.is(this.data[this.data.length - 1]));

        this.stack.clear();
        Assert.assertThat(this.stack.size() , Matchers.is(0));
    }

    @Test
    public void testLogic() {
        Assert.assertThat(this.stack.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.stack.push(this.data[i]);

        Assert.assertThat(this.stack.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.stack.peek() , Matchers.is(this.data[this.data.length - 1]));

        do
            this.stack.pop();
        while (this.stack.size() != 0);

        Assert.assertThat(this.stack.size() , Matchers.is(0));
    }
}
