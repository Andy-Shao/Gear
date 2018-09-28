package com.github.andyshao.data.structure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SingleLinkedTest {

    private final String[] data = new String[] { "Andy" , "Shao" , "andy" , "shao" };
    private SingleLinked<String> linked;

    @BeforeEach
    public void before() {
        this.linked = SingleLinked.<String> defaultSingleLinked(CycleLinkedElmt::defaultElmt);
    }

    @Test
    public void testDestroy() {
        this.testInsert();

        assertEquals(this.linked.size() , this.data.length);

        this.linked.clear();

        assertEquals(this.linked.size() , 0);
    }

    @Test
    public void testInsert() {
        assertEquals(this.linked.size() , 0);

        for (int i = 0 ; i < this.data.length ; i++)
            this.linked.insNext(this.linked.tail() , this.data[i]);

        assertEquals(this.linked.size() , this.data.length);
        assertEquals(this.linked.head().data() , this.data[0]);
    }

    @Test
    public void testItrator() {
        this.testInsert();

        String string = "";
        for (String str : this.linked)
            string += str;

        assertEquals(string , "AndyShaoandyshao");
    }

    @Test
    public void testRemove() {
        this.testInsert();
        assertEquals(this.linked.size() , this.data.length);

        this.linked.clear();
        assertEquals(this.linked.size() , 0);

        this.testInsert();
        assertEquals(this.linked.size() , this.data.length);

        this.linked.remNext(this.linked.head());
        assertEquals(this.linked.size() , this.data.length - 1);
    }

    @Test
    public void testSize() {
        assertEquals(this.linked.size() , 0);

        this.testInsert();

        assertEquals(this.linked.size() , this.data.length);
    }
}
