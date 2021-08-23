package org.gl.attributehook.utils.number;

import org.jetbrains.annotations.Nullable;

import java.util.regex.Pattern;

public final class NumberDetermines {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[+|-]?[0-9]+(\\.[0-9]+)?$");

    private NumberDetermines() {
    }

    public static boolean isNumber(@Nullable Object obj) {
        return !Numbers.isEmpty(obj) && NUMBER_PATTERN.matcher(obj.toString()).matches();
    }

    public static boolean isDigits(@Nullable Object obj) {
        if (Numbers.isEmpty(obj)) {
            return false;
        }
        String string = obj.toString();
        for (int i = 0; i < string.length(); i++) {
            if (!Character.isDigit(string.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
