package org.gl.attributehook.utils.jar;

import org.gl.attributehook.utils.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FilenameFilter;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ClassLoaderUtil {

    public static @NotNull List<Class<?>> forFolder(@NotNull ClassLoader classLoader, @NotNull File folder, @Nullable Class<? extends Annotation> annotation) {
        List<Class<?>> classList = new ArrayList<>();
        try {
            if (!folder.exists()) {
                return classList;
            }
            FilenameFilter fileNameFilter = (dir, name) -> name.endsWith(".jar");
            File[] jars = folder.listFiles(fileNameFilter);
            if (jars == null) {
                return classList;
            }
            for (File file : jars) {
                URL jar = file.toURI().toURL();
                URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jar}, classLoader);
                JarInputStream jarInputStream = new JarInputStream(jar.openStream());
                while (true) {
                    JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                    if (nextJarEntry == null) {
                        break;
                    }
                    String name = getName(nextJarEntry);
                    if (name == null) {
                        continue;
                    }
                    add(annotation, classList, urlClassLoader, name);
                }
            }
            return classList;
        } catch (Throwable ignored) {
        }
        return classList;
    }
    public static @NotNull List<Class<?>> forName(@NotNull Class<?> cls) {
        return forName(cls, null, null);
    }

    public static @NotNull List<Class<?>> forName(@NotNull Class<?> cls, @Nullable Class<? extends Annotation> a) {
        return forName(cls, null, a);
    }

    public static @NotNull List<Class<?>> forName(@NotNull Class<?> cls, @Nullable String forName, @Nullable Class<? extends Annotation> a) {
        List<Class<?>> classList = new ArrayList<>();
        try {

            forName = Strings.isEmpty(forName) ? "" : forName;

            URL jar = new File(URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(), StandardCharsets.UTF_8.toString())).toURI().toURL();
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{jar}, cls.getClassLoader());
            JarInputStream jarInputStream = new JarInputStream(jar.openStream());

            while (true) {
                JarEntry nextJarEntry = jarInputStream.getNextJarEntry();
                if (nextJarEntry == null) {
                    break;
                }

                String name = getName(nextJarEntry);

                if (Strings.nonEmpty(name) && name.startsWith(forName)) {
                    add(a, classList, urlClassLoader, name);
                }
            }
        } catch (Exception ignored) {
        }
        return classList;
    }

    private static void add(@Nullable Class<? extends Annotation> a, List<Class<?>> classList, URLClassLoader urlClassLoader, String name) throws ClassNotFoundException {
        String cname = name.substring(0, name.lastIndexOf(".class"));
        Class<?> loadClass = urlClassLoader.loadClass(cname);

        if (a == null) {
            classList.add(loadClass);
        }else {
            if (loadClass.isAnnotationPresent(a)) classList.add(loadClass);
        }
    }

    private static String getName(JarEntry jarEntry) {
        String name = jarEntry.getName();
        if (name.isEmpty()) {
            return null;
        }
        if (!name.endsWith(".class")) {
            return null;
        }
        return name.replace("/", ".");
    }

}
