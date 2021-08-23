package org.gl.attributehook.utils.number;

import org.jetbrains.annotations.NotNull;

public final class NumberDecimal {

    private NumberDecimal() {
    }

    @NotNull
    public static NumDecimal add(@NotNull Number n1, @NotNull Number n2) {
        return new NumDecimal().add(n1).add(n2);
    }

    @NotNull
    public static NumDecimal subtract(@NotNull Number n1, @NotNull Number n2) {
        return new NumDecimal().add(n1).subtract(n2);
    }

    @NotNull
    public static NumDecimal multiply(@NotNull Number n1, @NotNull Number n2) {
        return new NumDecimal().add(n1).multiply(n2);
    }

    @NotNull
    public static NumDecimal divide(@NotNull Number n1, @NotNull Number n2) {
        return new NumDecimal().add(n1).divide(n2);
    }
}
