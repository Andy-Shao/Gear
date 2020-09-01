package com.github.andyshao.reflect;

import com.github.andyshao.lang.GeneralSystemProperty;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
    public static String[] getClasses(Path jarPath) {
        List<String> result = new ArrayList<>();
        try (JarFile jar = new JarFile(jarPath.toFile());) {
            Enumeration<JarEntry> entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                //The jar entry which end with '/' is a package
                if (name.charAt(0) == '/') name = name.substring(1);
                if (name.endsWith(".class") && !entry.isDirectory()) {
                    String className = name.substring(0 , name.length() - 6).replace('/' , '.');
                    result.add(className);
                }
            }
        } catch (IOException e) {
            throw new ReflectiveOperationException(e);
        }
        return result.toArray(new String[result.size()]);
    }

    public static String[] getPackageClasseName(Package pkg) {
        List<String> result = new ArrayList<>();
        String packageName = pkg.getName();
        String packageDirName = packageName.replace('.' , '/');
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                final String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile() , GeneralSystemProperty.FILE_ENCODING.value());
                    File dir = new File(filePath);
                    if (!dir.exists() || !dir.isDirectory()) ;
                    File[] dirFiles = dir.listFiles((file) -> file.isDirectory() || file.getName().endsWith(".class"));
                    for (File file : dirFiles) {
                        if (file.isDirectory()) continue;
                        String className = file.getName().substring(0 , file.getName().length() - 6);
                        result.add(packageName + '.' + className);
                    }
                } else if ("jar".equals(protocol)) try (JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        //The jar entry which end with '/' is a package
                        if (name.charAt(0) == '/') name = name.substring(1);
                        if (name.startsWith(packageDirName) && name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(0 , name.length() - 6).replace('/' , '.');
                            result.add(className);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(e);
        }
        return result.toArray(new String[result.size()]);
    }

    public static Class<?>[] getPackageClasses(Package pkg) {
        List<Class<?>> result = new ArrayList<>();
        String packageName = pkg.getName();
        String packageDirName = packageName.replace('.' , '/');
        try {
            Enumeration<URL> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                final String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile() , GeneralSystemProperty.FILE_ENCODING.value());
                    File dir = new File(filePath);
                    if (!dir.exists() || !dir.isDirectory()) ;
                    File[] dirFiles = dir.listFiles((file) -> file.isDirectory() || file.getName().endsWith(".class"));
                    for (File file : dirFiles) {
                        if (file.isDirectory()) continue;
                        String className = file.getName().substring(0 , file.getName().length() - 6);
                        result.add(ClassOperation.forName(packageName + '.' + className));
                    }
                } else if ("jar".equals(protocol)) try (JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();
                        //The jar entry which end with '/' is a package
                        if (name.charAt(0) == '/') name = name.substring(1);
                        if (name.startsWith(packageDirName) && name.endsWith(".class") && !entry.isDirectory()) {
                            String className = name.substring(0 , name.length() - 6).replace('/' , '.');
                            result.add(ClassOperation.forName(className));
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new ClassNotFoundException(e);
        }
        return result.toArray(new Class<?>[result.size()]);
    }

    @Deprecated(since = "4.0.0.RELEASE")
    public static Package[] getPackages(String regex) {
        List<Package> result = new ArrayList<>();
        Package[] packages = Package.getPackages();
        for (Package pkg : packages)
            if (pkg.getName().matches(regex)) result.add(pkg);
        return result.toArray(new Package[result.size()]);
    }

    public static Package[] getPckages(Package basePackage) {
        List<Package> result = new ArrayList<>();
        Package[] packages = Package.getPackages();
        for (Package ppkg : packages)
            if (ppkg.getName().startsWith(basePackage.getName())) result.add(ppkg);
        return result.toArray(new Package[result.size()]);
    }

    private PackageOperation() {
        throw new AssertionError("No support instance " + PackageOperation.class + " for you!");
    }
}
