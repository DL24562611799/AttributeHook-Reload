package org.gl.attributehook.utils;

import org.gl.attributehook.utils.number.NumberTransform;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public final class VersionUtil {

    private static final List<String> rangeChat = Arrays.asList("~", "-", "|");

    public static boolean meet(String version, String max) {
        double v = toVersionInto10(version);
        double m = toVersionInto10(max);
        return v >= m;
    }

    public static boolean isCompatible(String version, String range) {
        String v = range.replaceAll(" ", "");
        for (String c : rangeChat) {
            if (v.contains(c)) {
                String[] split = v.split(c);
                return isCompatible(version, split[0], split[1]);
            }
        }
        return isCompatible(version, v, null);
    }

    private static boolean isCompatible(String version, String min, String max) {
        double v = toVersionInto10(version);
        double i = toVersionInto10(min);
        double a = toVersionInto10(max);
        return a > -1 ? a > i ? (v >= i && v < a) : (v >= i) : (v >= i);
    }

    private static double toVersionInto10(@Nullable String str) {
        if (Strings.isEmpty(str)) {
            return -1.0;
        }
        // 1.0.1-Bate
        String v;
        if (str.contains("-")) {
            v = str.split("-")[0];
        }else {
            v = str;
        }
        // v = 1.0.1
        v = v.replaceAll("[a-zA-Z]*", "");
        StringBuilder builder = new StringBuilder();

        if (v.contains(".")) {
            // {1, 0, 1}
            String[] splits = v.split("\\.");
            // b = 10;
            builder.append(splits[0]).append(splits[1]);
            if (splits.length > 2) {
                builder.append(".");
                for (int i = 2; i < splits.length; i++) {
                    builder.append(splits[i]);
                }
            }
        }else {
            builder.append(v).append("0");
        }
        return NumberTransform.toDouble(builder.toString());
    }
}
