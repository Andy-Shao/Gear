package com.github.andyshao.reflect;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class PackageOperationTest {

    @Test
    public void testGetClassByJarPath() throws URISyntaxException {
//        String jarPath = GeneralSystemProperty.JAVA_HOME.value() + "/lib/rt.jar";//after jdk9 rt.jar does not exist
//        String[] classes = PackageOperation.getClasses(Paths.get(jarPath));
//        Assert.assertThat(ArrayOperation.indexOf(classes , "java.lang.String") != -1 , Matchers.is(true));
    	
		URL url = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/reflect/my.jar");
		String[] classes = PackageOperation.getClasses(Paths.get(url.toURI()));
		Assert.assertThat(ArrayOperation.indexOf(classes, "my.test.My") != -1, Matchers.is(true));
    }

    @Test
    public void testGetPackageClasses() {
        Class<?>[] classes = PackageOperation.getPackageClasses(Package.getPackage("com.github.andyshao.reflect"));
        Assert.assertThat(classes.length , Matchers.not(0));
        Assert.assertThat(ArrayOperation.indexOf(classes , ClassOperation.forName("com.github.andyshao.reflect.PackageOperationTest")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(classes , ClassOperation.forName("com.github.andyshao.reflect.PackageOperation")) , Matchers.not(-1));

        classes = PackageOperation.getPackageClasses(Package.getPackage("org.objectweb.asm"));
    }

    @Test
    public void testGetPackages() {
        Package[] packages = PackageOperation.getPckages(Package.getPackage("java.util"));
        Assert.assertThat(packages.length , Matchers.not(0));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.zip")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.regex")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent.atomic")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.function")) , Matchers.not(-1));
        Assert.assertThat(ArrayOperation.indexOf(packages , Package.getPackage("java.util.jar")) , Matchers.not(-1));
    }
}
