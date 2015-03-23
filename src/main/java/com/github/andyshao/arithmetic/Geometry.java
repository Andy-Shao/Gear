package com.github.andyshao.arithmetic;

import java.util.function.Function;

/**
 * 
 * Title:<br>
 * Descript:几何算法<br>
 * Copyright: Copryright(c) Mar 23, 2015<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class Geometry {
    public class Point {
        public double x , y , z;
    }

    public class Spoint {
        public double rho , theta , phi;
    }

    public static final Function<Double , Double> OEGTORAD = (deg) -> (deg * 2.0 * Geometry.PI) / 360.0;
    public static final double PI = 3.14159;
    public static final Function<Double , Double> RADTODEG = (rad) -> (rad * 360.0) / (2.0 * Geometry.PI);

    private Geometry() {
        throw new AssertionError("No " + Geometry.class + " for you!");
    }
}
