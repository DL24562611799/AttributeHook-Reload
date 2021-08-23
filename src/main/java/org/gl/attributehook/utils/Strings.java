package org.gl.attributehook.utils;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public final class Strings {

    private Strings() {
    }

    public static boolean nonEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 优化过的 String#replace，比默认快了大概 5 倍
     * @param str str
     * @param params params
     * @return 替换好的字符串
     */
    public static String replace(@NotNull String str, Object... params) {
        if (params.length == 0 || str.length() == 0) {
            return ChatColor.translateAlternateColorCodes('&', str);
        }
        char[] arr = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder(str.length());
        for (int i = 0; i < arr.length; i++) {
            int mark = i;
            if (arr[i] == '{') {
                int num = 0;
                while (i + 1 < arr.length && Character.isDigit(arr[i + 1])) {
                    i++;
                    num *= 10;
                    num += arr[i] - '0';
                }
                if (i != mark && i + 1 < arr.length && arr[i + 1] == '}') {
                    i++;
                    if (params.length > num) stringBuilder.append(params[num]);
                } else {
                    i = mark;
                }
            }
            if (mark == i) {
                stringBuilder.append(arr[i]);
            }
        }
        return ChatColor.translateAlternateColorCodes('&', stringBuilder.toString());
    }

    /**
     * 清空字符串
     * @param str str
     * @return String
     */
    public static String clearColorCode(String str) {
        char[] chars = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        boolean isContinue = false;
        for (char c : chars) {
            if (c == '§') {
                isContinue = true;
                continue;
            }
            if (isContinue) {
                isContinue = false;
                continue;
            }
            builder.append(c);
        }
        return builder.toString();
    }


    /**
     * 首字母大写
     * @param str str
     * @return String
     */
    public static String capture(String str) {
        char[] chars = str.toCharArray();
        if (Character.isLowerCase(chars[0])) {
            chars[0]-=32;
            return String.valueOf(chars);
        }
        return str;
    }
}
