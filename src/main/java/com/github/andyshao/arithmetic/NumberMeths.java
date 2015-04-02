package com.github.andyshao.arithmetic;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

import com.github.andyshao.lang.AutoIncreaseArray;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Mar 5, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class NumberMeths {

    /**
     * interpol (多项式插值)
     * 
     * @param x x values
     * @param fx f(x) values list.
     * @param z the points which are asked.
     * @param scale scale of the {@code BigDecimal} quotient to be returned.
     * @param roundingMode rounding mode to apply.
     * @return the answer list of f(z)
     */
    public static final BigDecimal[] interpol(
        final BigDecimal[] x , final BigDecimal[] fx , BigDecimal[] z , int scale , RoundingMode roundingMode) {
        BigDecimal[] pz = new BigDecimal[z.length];
        BigDecimal[] coeff = new BigDecimal[x.length];

        //Determine the coefficients of the interpolating polynomial.
        {
            BigDecimal[] table = new BigDecimal[x.length];
            System.arraycopy(fx , 0 , table , 0 , table.length);
            coeff[0] = table[0];
            for (int k = 1 ; k < x.length ; k++) {
                for (int i = 0 ; i < x.length - k ; i++) {
                    int j = i + k;
                    table[i] = table[i + 1].subtract(table[i]).divide(x[j].subtract(x[i]) , scale , roundingMode);
                }
                coeff[k] = table[0];
            }
        }

        //Evaluate the interpolating polynomial at the specified points.
        for (int k = 0 ; k < z.length ; k++) {
            pz[k] = coeff[0];
            for (int j = 1 ; j < x.length ; j++) {
                BigDecimal term = coeff[j];
                for (int i = 0 ; i < j ; i++)
                    term = term.multiply(z[k].subtract(x[j]));
                pz[k] = pz[k].add(term);
            }
        }

        return pz;
    }

    /**
     * interpol (多项式插值)
     * 
     * @param x x values
     * @param fx f(x) values list.
     * @param z the points which are asked.
     * @return the answer list of f(z)
     */
    public static final double[] interpol(final double[] x , final double[] fx , double[] z) {
        double[] pz = new double[z.length];
        double[] coeff = new double[x.length];

        //Determine the coefficients of the interpolating polynomial.
        {
            double[] table = new double[x.length];
            System.arraycopy(fx , 0 , table , 0 , table.length);
            coeff[0] = table[0];
            for (int k = 1 ; k < x.length ; k++) {
                for (int i = 0 ; i < x.length - k ; i++) {
                    int j = i + k;
                    table[i] = (table[i + 1] - table[i]) / (x[j] - x[i]);
                }
                coeff[k] = table[0];
            }
        }

        //Evaluate the interpolating polynomial at the specified points.
        for (int k = 0 ; k < z.length ; k++) {
            pz[k] = coeff[0];
            for (int j = 1 ; j < x.length ; j++) {
                double term = coeff[j];
                for (int i = 0 ; i < j ; i++)
                    term = term * (z[k] - x[j]);
                pz[k] = pz[k] + term;
            }
        }

        return pz;
    }

    /**
     * (方程求解)
     * 
     * @param f f(x)
     * @param g f'(x)
     * @param x x[0] is x
     * @param maxTimes the limit times.
     * @param delta 允许的最大偏差
     * @param scale scale of the {@code BigDecimal} quotient to be returned.
     * @param roundingMode rounding mode to apply.
     * @return a Double[]
     */
    public static final BigDecimal[] root(
        Function<BigDecimal , BigDecimal> f , Function<BigDecimal , BigDecimal> g , AutoIncreaseArray<BigDecimal> x ,
        int maxTimes , BigDecimal delta , int scale , RoundingMode roundingMode) {
        if (maxTimes < 0) throw new IllegalArgumentException();
        //User Newton's method to find a root of f.
        for (int i = 0 , satisfied = 0 ; satisfied == 0 && i + 1 < maxTimes ; i++) {
            //Determine the next iteration of x.
            BigDecimal temp = x.get(i);
            x.set(temp.subtract(f.apply(temp).divide(g.apply(temp) , scale , roundingMode)) , i + 1);

            //Determine whether the desired approximation has been obtained.
            if (x.get(i + 1).subtract(x.get(i)).abs().compareTo(delta) < 0) satisfied = 1;
        }
        return x.toArray(new BigDecimal[x.size()]);
    }

    /**
     * (方程求解)
     * 
     * @param f f(x)
     * @param g f'(x)
     * @param x x[0] is x
     * @param maxTimes the limit times.
     * @param delta 允许的最大偏差
     * @return a Double[]
     */
    public static final Double[] root(
        Function<Double , Double> f , Function<Double , Double> g , AutoIncreaseArray<Double> x , int maxTimes ,
        double delta) {
        if (maxTimes < 0) throw new IllegalArgumentException();
        //User Newton's method to find a root of f.
        for (int i = 0 , satisfied = 0 ; satisfied == 0 && i + 1 < maxTimes ; i++) {
            //Determine the next iteration of x.
            Double temp = x.get(i);
            x.set(temp - (f.apply(temp) / g.apply(temp)) , i + 1);

            //Determine whether the desired approximation has been obtained.
            if (Math.abs(x.get(i + 1) - x.get(i)) < delta) satisfied = 1;
        }
        return x.toArray(new Double[x.size()]);
    }

    public NumberMeths() {
        throw new AssertionError("No " + NumberMeths.class.getName() + " instances for you!");
    }
}
