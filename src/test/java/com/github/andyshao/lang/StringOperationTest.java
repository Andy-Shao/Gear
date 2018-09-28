package com.github.andyshao.lang;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringOperationTest {
    private volatile String str;

    @BeforeEach
    public void before() {
        this.str = "178231785178";
    }

    @Test
    public void testFlipString() {
        assertEquals(StringOperation.flipString("123") , ("321"));
    }

    @Test
    public void testisEmptyOrNull() {
        assertEquals(StringOperation.isEmptyOrNull(null) , (true));
        assertEquals(StringOperation.isEmptyOrNull("") , (true));
        assertEquals(StringOperation.isEmptyOrNull(this.str) , (false));
    }

    @Test
    public void testreplaceAll() {
        assertEquals(StringOperation.replaceAll(this.str , "178" , "") , ("235"));
    }

    @Test
    public void testreplaceFirst() {
        assertEquals(StringOperation.replaceFirst(this.str , "178" , "") , ("231785178"));
    }

    @Test
    public void testreplaceLast() {
        assertEquals(StringOperation.replaceLast(this.str , "178" , "") , ("178231785"));
    }

    @Test
    public void testsplit() {
        assertArrayEquals(StringOperation.split(this.str , "231") , new String[] { "178" , "785178" });
    }
}
