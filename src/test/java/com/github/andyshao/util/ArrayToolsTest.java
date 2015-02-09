package com.github.andyshao.util;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.convert.Convert;

/**
 * 
 * @author Andy
 * 
 */
public class ArrayToolsTest {

    @Test
    public void convertToMap() {
        Map<String , String> target =
            ArrayTools.<String , String> convertToMap(Convert.OB_2_STR , Convert.OB_2_STR ,
                new HashMap<String , String>() , new Object[][] {
                    {
                        "i" , "2"
                    } , {
                        "I" , "3"
                    } , {
                        "key" , "value"
                    }
                });

        Map<String , String> answer = new HashMap<>();
        answer.put("i" , "2");
        answer.put("I" , "3");
        answer.put("key" , "value");

        Assert.assertThat(answer , Matchers.is(target));
    }

    @Test
    public void findFirstItem() {
        int[] array = new int[] {
            1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9
        };
        Assert.assertThat(ArrayTools.findFirstItem(array , 4) , Matchers.is(3));
    }

    @Test
    public void findLastItem() {
        int[] array = new int[] {
            1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9
        };
        Assert.assertThat(ArrayTools.findFirstItem(array , 4) , Matchers.is(3));
    }

    @Test
    public void flipArray() {
        int[] array = new int[] {
            1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9
        };
        int[] array2 = new int[] {
            9 , 8 , 7 , 6 , 5 , 4 , 3 , 2 , 1
        };
        Assert.assertThat(ArrayTools.flipArray(array) , Matchers.is(array2));
    }

    @Test
    public void mergeArray() {
        {
            int[] array = ArrayTools.mergeArray(int[].class , new int[] {
                1 , 2 , 3 , 4
            } , new int[] {
                5 , 6 , 7 , 8 , 9
            });
            Assert.assertThat(array , Matchers.is(new int[] {
                1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9
            }));
        }
        {
            char[] cs = new char[] {
                'b' , 'c'
            };
            char[] array = ArrayTools.mergeArray(char[].class , cs , new char[0]);
            Assert.assertThat(array , Matchers.is(cs));

            array = ArrayTools.mergeArray(char[].class , new char[0] , cs);
            Assert.assertThat(array , Matchers.is(cs));

            array = ArrayTools.mergeArray(char[].class , new char[0] , new char[0]);
            Assert.assertThat(array , Matchers.is(new char[0]));
        }
    }

    @Test
    public void pack_unpack() {
        {
            Integer[] integers = ArrayTools.pack_unpack(new int[] {
                1 , 2 , 3
            } , Integer[].class);
            Assert.assertThat(integers , Matchers.is(new Integer[] {
                1 , 2 , 3
            }));
        }

        {
            int[] is = ArrayTools.pack_unpack(new Integer[] {
                1 , 2 , 3
            } , int[].class);
            Assert.assertThat(is , Matchers.is(new int[] {
                1 , 2 , 3
            }));
        }

        {
            long[] ls = ArrayTools.pack_unpack(new int[] {
                1
            } , long[].class);
            Assert.assertThat(ls , Matchers.is(new long[] {
                1
            }));
        }
    }

    @Test
    public void removeAllItem() {
        int[] array = new int[] {
            1 , 2 , 3 , 4 , 3 , 6 , 7 , 3 , 9
        };
        Assert.assertThat(ArrayTools.removeAllItem(array , 3) , Matchers.is(new int[] {
            1 , 2 , 4 , 6 , 7 , 9
        }));
    }

    @Test
    public void removeItem() {
        int[] array = new int[] {
            1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9
        };
        Assert.assertThat(ArrayTools.removeItem(array , 3) , Matchers.is(new int[] {
            1 , 2 , 3 , 5 , 6 , 7 , 8 , 9
        }));
        Assert.assertThat(ArrayTools.removeItem(array , 1 , 4) , Matchers.is(new int[] {
            1 , 5 , 6 , 7 , 8 , 9
        }));
    }
}
