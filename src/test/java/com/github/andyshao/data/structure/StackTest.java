package com.github.andyshao.data.structure;

import java.util.Arrays;
import java.util.Collection;

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
        return Arrays.asList(new Object[][] {
            {
                Stack
                    .<String , SingleLinked.SingleLinkedElmt<String> , SingleLinked<String>> DEFAULT_STACK(SingleLinked
                        .DEFAULT_SINGLE_LINKED())
            } ,
            {
                Stack.<String , CycleLinked.CycleLinkedElmt<String> , CycleLinked<String>> DEFAULT_STACK(CycleLinked
                    .DEFAULT_CYCLE_LINKED())
            }
        });
    }

    private final String[] data = {
        "Andy" , "Shao"
    };

    private volatile Stack<String> stack;

    public StackTest(Stack<String> stack) {
        this.stack = stack;
    }

    @Test
    public void testLogic() {
        Assert.assertThat(this.stack.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.stack.push(this.data[i]);

        Assert.assertThat(this.stack.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.stack.peek() , Matchers.is(this.data[this.data.length-1]));

        do
            this.stack.pop();
        while (this.stack.size() != 0);

        Assert.assertThat(this.stack.size() , Matchers.is(0));
    }
}