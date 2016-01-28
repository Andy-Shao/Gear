package com.github.andyshao.reflect;

/**
 * 
 * Title:<br>
 * Descript:<br>
 * Copyright: Copryright(c) Jan 24, 2016<br>
 * Encoding:UNIX UTF-8
 * 
 * @author Andy.Shao
 *
 */
public final class PackageOperation {
    private PackageOperation() {
        throw new AssertionError("No support instance " + PackageOperation.class + " for you!");
    }
    
    public static Class<?>[] getPackageClasses(Package pkg){
        //TODO
        return null;
    }
}
