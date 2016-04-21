package com.github.andyshao.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.function.Function;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import com.github.andyshao.reflect.ArrayOperation;

public class NumberMethsTest {

    @Test
    public void testinterpol() {
        final double[] x = new double[] { -3.0 , -2.0 , 2.0 , 3.0 };
        Function<Object , Object> function = (input) -> new BigDecimal(Objects.toString(input));
        final BigDecimal[] xX = ArrayOperation.pack_unpack(x , BigDecimal[].class , function);
        final double[] fx = new double[] { -5.0 , -1.1 , 1.9 , 4.8 };
        final BigDecimal[] fxX = ArrayOperation.pack_unpack(fx , BigDecimal[].class , function);
        final double[] z = new double[] { -2.5 , 0.0 , 2.5 };
        final BigDecimal[] zX = ArrayOperation.pack_unpack(z , BigDecimal[].class , function);

        double[] pz = NumberMeths.interpol(x , fx , z);
        Assert.assertThat(pz , Matchers.is(new double[] { -49.10041666666666 , -4.49 , 12.370416666666667 }));

        BigDecimal[] pzX = NumberMeths.interpol(xX , fxX , zX , 20 , RoundingMode.HALF_EVEN);
        Assert.assertThat(pzX ,
            Matchers.is(ArrayOperation.pack_unpack(new String[] { "-49.10041666666666666722125" , "-4.49000000000000000009000" , "12.37041666666666666666625" } , BigDecimal[].class , function)));
    }
}
