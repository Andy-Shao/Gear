package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AutoIncreaseArrayTest {

    private volatile AutoIncreaseArray<Character> array;
    private final Character[] data =
        new Character[] { 'a' , 'b' , 'c' , 'd' , 'e' , 'f' , 'g' , 'h' , 'i' , 'j' , 'k' , 'l' , 'm' , 'n' , 'o' , 'p' , 'q' , 'r' , 's' , 't' , 'u' , 'v' , 'w' , 'x' , 'y' , 'z' };

    @BeforeEach
    public void before() {
        this.array = new AutoIncreaseArray<Character>();
    }

    @Test
    public void testForeach() {
        this.testInsertTail();

        String str = "";
        for (Character c : this.array)
            str += c;

        assertEquals(str , "abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    public void testInsertHead() {
       assertEquals(this.array.size() , 0);

        for (Character c : this.data)
            this.array.addHead(c);

        assertEquals(this.array.size() , this.data.length);
        assertEquals(this.array.get(0) , this.data[this.data.length - 1]);
        assertEquals(this.array.get(this.data.length - 1) , this.data[0]);
    }

    @Test
    public void testInsertTail() {
        assertEquals(this.array.size() , 0);

        for (Character c : this.data)
            this.array.addTail(c);

        assertEquals(this.array.size() , this.data.length);
        assertEquals(this.array.get(0) , this.data[0]);
        assertEquals(this.array.get(this.data.length - 1) , this.data[this.data.length - 1]);
    }

    @Test
    public void testRemove() {
        this.testInsertTail();

        Character c = this.array.remove(this.data.length - 1);

        assertEquals(this.array.size() , this.data.length - 1);
        assertEquals(c , Character.valueOf('z'));
    }
}
