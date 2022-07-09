package org.ignamlrz.autotrader.core.utilities;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class ClassLoaderUtils {

    /**
     * Method for find all classes
     *
     * @param packageName Target package name
     * @return all classes found
     */
    public static Set<Class> findAllClasses(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        if (stream == null) return new HashSet<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
                .filter(line -> line.endsWith(".class"))
                .map(line -> getClass(line, packageName))
                .collect(Collectors.toSet());
    }

    /**
     * Method for get class given a package and a classname
     *
     * @param className   Class name
     * @param packageName Package name
     * @return a class
     */
    private static Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                    + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // ...handle the exception
            return null;
        }
    }
}
