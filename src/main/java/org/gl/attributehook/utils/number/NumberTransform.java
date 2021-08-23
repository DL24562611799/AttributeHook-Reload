package org.gl.attributehook.utils.number;

import org.jetbrains.annotations.Nullable;

public final class NumberTransform {
    private static final String CLEAR_LATTER = "\u00a7+[a-z0-9]";
    private static final String CLEAR_LINE= "[^-0-9.]";

    private NumberTransform() {
    }

    public static Number extractNumber(@Nullable Object obj) {
        return extractNumber(obj, 0);
    }

    public static Number extractNumber(@Nullable Object obj, Number def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        if (NumberDetermines.isNumber(string)) {
            return new java.math.BigDecimal(string);
        }else {
            String number = string.replaceAll(CLEAR_LATTER, "").replaceAll(CLEAR_LINE, "");
            return number.length() == 0 || number.replaceAll("[^.]", "").length() > 1 ? 0 : new java.math.BigDecimal(number);
        }
    }

    public static int toInt(@Nullable Object obj) {
        return toInt(obj, 0);
    }

    public static int toInt(@Nullable Object obj, int def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Integer.parseInt(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static long toLong(@Nullable Object obj) {
        return toLong(obj, 0);
    }

    public static long toLong(@Nullable Object obj, long def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Long.parseLong(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static double toDouble(@Nullable Object obj) {
        return toDouble(obj, 0);
    }

    public static double toDouble(@Nullable Object obj, double def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Double.parseDouble(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static float toFloat(@Nullable Object obj) {
        return toFloat(obj, 0);
    }

    public static float toFloat(@Nullable Object obj, float def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Float.parseFloat(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static byte toByte(@Nullable Object obj) {
        return toByte(obj, (byte) 0);
    }

    public static byte toByte(@Nullable Object obj, byte def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Byte.parseByte(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static short toShort(@Nullable Object obj) {
        return toShort(obj, (short) 0);
    }

    public static short toShort(@Nullable Object obj, short def) {
        if (Numbers.isEmpty(obj)) {
            return def;
        }
        String string = obj.toString();
        try {
            return Short.parseShort(string);
        }catch (NumberFormatException e) {
            return def;
        }
    }

    public static long max(long[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        long max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }
        return max;
    }

    public static int max(int[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        int max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static short max(short[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }
        // Finds and returns max
        short max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static byte max(byte[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        byte max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        return max;
    }

    public static double max(double[] array) {
        // Validates input
        if (array== null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        double max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Double.isNaN(array[j])) {
                return Double.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static float max(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns max
        float max = array[0];
        for (int j = 1; j < array.length; j++) {
            if (Float.isNaN(array[j])) {
                return Float.NaN;
            }
            if (array[j] > max) {
                max = array[j];
            }
        }

        return max;
    }

    public static long min(long[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        long min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static int min(int[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        int min = array[0];
        for (int j = 1; j < array.length; j++) {
            if (array[j] < min) {
                min = array[j];
            }
        }

        return min;
    }

    public static short min(short[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        short min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static byte min(byte[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        byte min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static double min(double[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        double min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Double.isNaN(array[i])) {
                return Double.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }

    public static float min(float[] array) {
        // Validates input
        if (array == null) {
            throw new IllegalArgumentException("The Array must not be null");
        } else if (array.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty.");
        }

        // Finds and returns min
        float min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (Float.isNaN(array[i])) {
                return Float.NaN;
            }
            if (array[i] < min) {
                min = array[i];
            }
        }

        return min;
    }
}
