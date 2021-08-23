package org.gl.attributehook.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ReflexUtil {

    private ReflexUtil() {
    }

    @NotNull
    public static Constructor<?> getConstructor(@NotNull Class<?> cls, Class<?>... params) {
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (compareParams(types, params)) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        throw new NullPointerException("There is no such constructor in this class with the specified parameter types");
    }

    @NotNull
    public static Constructor<?> getConstructor(@NotNull Class<?> cls, Object... params) {
        return getConstructor(cls, toParams(params));
    }

    @NotNull
    public static Method getMethod(@NotNull Class<?> cls, @NotNull String name, Class<?>... params) {
        for (Method method : cls.getDeclaredMethods()) {
            System.out.println(method.getName());
            if (method.getName().equals(name) && compareParams(method.getParameterTypes(), params)) {
                method.setAccessible(true);
                return method;
            }
        }
        throw new NullPointerException("There is no such method in this class with the specified name and parameter types");
    }

    public static Method getMethod(@NotNull Class<?> cls, @NotNull String name, Object... params) {
        return getMethod(cls, name, toParams(params));
    }

    @NotNull
    public static Field getField(@NotNull Class<?> cls, @NotNull String name) {
        for (Field field : getFields(cls)) {
            if (field.getName().equals(name)) {
                field.setAccessible(true);
                return field;
            }
        }
        throw new NullPointerException("There is no such field in this class with the specified name");
    }

    public static List<Field> getFields(Class<?> cls) {
        List<Field> list = new ArrayList<>(Arrays.asList(cls.getDeclaredFields()));
        Class<?> superclass = cls.getSuperclass();
        if (superclass != null) {
            list.addAll(getFields(superclass));
        }
        return list;
    }

    public static Object newInstance(Class<?> cls, Object... params) {
        for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
            Class<?>[] types = constructor.getParameterTypes();
            if (compareParams(types, toParams(params))) {
                constructor.setAccessible(true);
                try {
                    return constructor.newInstance(params);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignored) {
                }
            }
        }
        return null;
    }

    public static Class<?>[] toParams(Object... params) {
        Class<?>[] classes = new Class<?>[params.length];
        for (int i = 0; i < params.length; i++) {
            classes[i] = params[i].getClass();
        }
        return classes;
    }

    public static boolean compareParams(Class<?>[] classes, Class<?>[] params) {
        if (classes != null && params != null && classes.length == params.length) {
            for (int i = 0; i < classes.length; i++) {
                Class<?> type = classes[i];
                Class<?> param = params[i];
                if (!type.equals(param) && !type.isAssignableFrom(param)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
