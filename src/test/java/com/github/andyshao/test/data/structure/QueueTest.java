package com.github.andyshao.test.data.structure;

import java.util.Arrays;
import java.util.Collection;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.github.andyshao.data.structure.CycleLinked;
import com.github.andyshao.data.structure.CycleLinkedElmt;
import com.github.andyshao.data.structure.SingleLinked;

@Deprecated
@RunWith(Parameterized.class)
public class QueueTest {

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
            { Queue.<String , CycleLinkedElmt<String> , SingleLinked<String>> DEFAULT_QUEUE(
                SingleLinked.defaultSingleLinked((data) -> CycleLinkedElmt.defaultElmt(data))) } ,
            { Queue.<String , CycleLinkedElmt<String> , CycleLinked<String>> DEFAULT_QUEUE(
                CycleLinked.defaultCycleLinked((data) -> CycleLinkedElmt.defaultElmt(data))) } });
    }

    public final String[] data = { "Andy" , "Shao" };
    public volatile Queue<String> queue;

    public QueueTest(Queue<String> queue) {
        this.queue = queue;
    }

    @Test
    public void testLogic() {
        Assert.assertThat(this.queue.size() , Matchers.is(0));

        for (int i = 0 ; i < this.data.length ; i++)
            this.queue.enqueue(this.data[i]);

        Assert.assertThat(this.queue.size() , Matchers.is(this.data.length));
        Assert.assertThat(this.queue.peek() , Matchers.is(this.data[0]));

        do
            this.queue.dequeue();
        while (this.queue.size() != 0);
    }
}
