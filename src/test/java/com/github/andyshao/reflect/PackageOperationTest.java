package com.github.andyshao.reflect;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

public class PackageOperationTest {

    @Test
    public void testGetClassByJarPath() throws URISyntaxException {
		URL url = Thread.currentThread().getContextClassLoader().getResource("com/github/andyshao/reflect/my.jar");
		String[] classes = PackageOperation.getClasses(Paths.get(url.toURI()));
		assertEquals(ArrayOperation.indexOf(classes, "my.test.My") != -1, (true));
    }

    @Test
    public void testGetPackageClasses() {
        Class<?>[] classes = PackageOperation.getPackageClasses(Package.getPackage("com.github.andyshao.reflect"));
        assertNotEquals(classes.length , (0));
        assertNotEquals(ArrayOperation.indexOf(classes , ClassOperation.forName("com.github.andyshao.reflect.PackageOperationTest")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(classes , ClassOperation.forName("com.github.andyshao.reflect.PackageOperation")) , (-1));

        classes = PackageOperation.getPackageClasses(Package.getPackage("java.lang"));
    }

    @Test
    public void testGetPackages() {
        Package[] packages = PackageOperation.getPackages(Package.getPackage("java.util"));
        assertNotEquals(packages.length , (0));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.zip")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.regex")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.concurrent.atomic")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.function")) , (-1));
        assertNotEquals(ArrayOperation.indexOf(packages , Package.getPackage("java.util.jar")) , (-1));
    }
}
