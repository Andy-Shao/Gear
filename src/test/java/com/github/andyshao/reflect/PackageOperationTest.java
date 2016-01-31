package com.github.andyshao.reflect;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class PackageOperationTest {

    @Test
    public void testGetPackageClasses() {
        Class<?>[] classes = PackageOperation.getPackageClasses(Package.getPackage("com.github.andyshao.reflect"));
        Assert.assertThat(classes.length , Matchers.not(0));
        Assert.assertThat(ArrayOperation.indexOf(classes ,
            ClassOperation.forName("com.github.andyshao.reflect.PackageOperationTest")) , Matchers.not(-1));
        Assert.assertThat(
            ArrayOperation.indexOf(classes , ClassOperation.forName("com.github.andyshao.reflect.PackageOperation")) ,
            Matchers.not(-1));

        classes = PackageOperation.getPackageClasses(Package.getPackage("org.objectweb.asm"));
    }

    @Test
    public void testGetPackages() {
        Package[] packages = PackageOperation.getPckages(Package.getPackage("java.util"));
        Assert.assertThat(packages.length , Matchers.not(0));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.zip")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.regex")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent")) ,
            Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent.atomic")) ,
            Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.function")) ,
            Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.jar")) , Matchers.not(-1));
    }
}
