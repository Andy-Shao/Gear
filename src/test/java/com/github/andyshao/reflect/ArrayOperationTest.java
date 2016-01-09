package com.github.andyshao.reflect;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.lang.Convert;

/**
 * 
 * @author Andy
 * 
 */
public class ArrayOperationTest {

    @Test
    public void convertToMap() {
        Map<String , String> target = ArrayOperation.<String , String> convertToMap(Convert.OB_2_STR ,
            Convert.OB_2_STR , new HashMap<String , String>() ,
            new Object[][] { { "i" , "2" } , { "I" , "3" } , { "key" , "value" } });

        Map<String , String> answer = new HashMap<>();
        answer.put("i" , "2");
        answer.put("I" , "3");
        answer.put("key" , "value");

        Assert.assertThat(answer , Matchers.is(target));
    }

    @Test
    public void findFirstItem() {
        int[] array = new int[] { 1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9 };
        Assert.assertThat(ArrayOperation.indexOf(array , 4) , Matchers.is(3));
    }

    @Test
    public void findLastItem() {
        int[] array = new int[] { 1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9 };
        Assert.assertThat(ArrayOperation.indexOf(array , 4) , Matchers.is(3));
    }

    @Test
    public void flipArray() {
        int[] array = new int[] { 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 };
        int[] array2 = new int[] { 9 , 8 , 7 , 6 , 5 , 4 , 3 , 2 , 1 };
        Assert.assertThat(ArrayOperation.flipArray(array) , Matchers.is(array2));
    }

    @Test
    public void indexOfAll() {
        final Byte[] bs = ArrayOperation.pack_unpack("aaannndddyyyaandyyyyaaannnddd".getBytes() , Byte[].class);
        final Byte[] key = ArrayOperation.pack_unpack("andy".getBytes() , Byte[].class);
        final int position = ArrayOperation.indexOfAll(bs , key);
        Assert.assertArrayEquals(ArrayOperation.splitArray(bs , position , key.length + position) , key);
    }

    @Test
    public void lastIndexOfAll() {
        final Byte[] bs = ArrayOperation.pack_unpack("aaannndddyyyandyy".getBytes() , Byte[].class);
        final Byte[] key = ArrayOperation.pack_unpack("andy".getBytes() , Byte[].class);
        final int position = ArrayOperation.lastIndexOfAll(bs , key);
        Assert.assertArrayEquals(ArrayOperation.splitArray(bs , position , position + key.length) , key);
    }

    @Test
    public void mergeArray() {
        {
            int[] array =
                ArrayOperation.mergeArray(int[].class , new int[] { 1 , 2 , 3 , 4 } , new int[] { 5 , 6 , 7 , 8 , 9 });
            Assert.assertThat(array , Matchers.is(new int[] { 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 }));
        }
        {
            char[] cs = new char[] { 'b' , 'c' };
            char[] array = ArrayOperation.mergeArray(char[].class , cs , new char[0]);
            Assert.assertThat(array , Matchers.is(cs));

            array = ArrayOperation.mergeArray(char[].class , new char[0] , cs);
            Assert.assertThat(array , Matchers.is(cs));

            array = ArrayOperation.mergeArray(char[].class , new char[0] , new char[0]);
            Assert.assertThat(array , Matchers.is(new char[0]));
        }
    }

    @Test
    public void pack_unpack() {
        {
            Integer[] integers = ArrayOperation.pack_unpack(new int[] { 1 , 2 , 3 } , Integer[].class);
            Assert.assertThat(integers , Matchers.is(new Integer[] { 1 , 2 , 3 }));
        }

        {
            int[] is = ArrayOperation.pack_unpack(new Integer[] { 1 , 2 , 3 } , int[].class);
            Assert.assertThat(is , Matchers.is(new int[] { 1 , 2 , 3 }));
        }

        {
            long[] ls = ArrayOperation.pack_unpack(new int[] { 1 } , long[].class);
            Assert.assertThat(ls , Matchers.is(new long[] { 1 }));
        }
    }

    @Test
    public void removeAllItem() {
        int[] array = new int[] { 1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9 };
        Assert.assertThat(ArrayOperation.removeAllItem(array , 3) , Matchers.is(new int[] { 1 , 2 , 4 , 6 , 7 , 9 }));
    }

    @Test
    public void removeItem() {
        int[] array = new int[] { 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 };
        Assert.assertThat(ArrayOperation.removeItem(array , 3) ,
            Matchers.is(new int[] { 1 , 2 , 3 , 5 , 6 , 7 , 8 , 9 }));
        Assert.assertThat(ArrayOperation.removeItem(array , 1 , 4) , Matchers.is(new int[] { 1 , 5 , 6 , 7 , 8 , 9 }));
    }
}
