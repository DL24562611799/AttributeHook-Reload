package org.gl.attributehook.utils.number;

import lombok.Getter;

import java.math.BigDecimal;

public class NumDecimal {

    @Getter
    private BigDecimal number = new BigDecimal("0");

    protected NumDecimal() {
    }

    public NumDecimal add(Number number) {
        this.number =  this.number.add(new BigDecimal(number.toString()));
        return this;
    }

    public NumDecimal subtract(Number number) {
        this.number =  this.number.subtract(new BigDecimal(number.toString()));
        return this;
    }

    public NumDecimal multiply(Number number) {
        this.number =  this.number.multiply(new BigDecimal(number.toString()));
        return this;
    }

    public NumDecimal divide(Number number) {
        this.number = this.number.divide(new BigDecimal(number.toString()), 2, BigDecimal.ROUND_HALF_UP);
        return this;
    }

    public int intValue() {
        return number.intValue();
    }

    public long longValue() {
        return number.longValue();
    }

    public double doubleValue() {
        return number.doubleValue();
    }

    public float floatValue() {
        return number.floatValue();
    }

    public byte byteValue() {
        return number.byteValue();
    }

    public short shortValue() {
        return number.shortValue();
    }
}
