package com.cairone.vo.enums;

public enum GenderEnum {
    MALE('M'),
    FEMALE('F'),
    NOT_BINARY('X');

    final char value;

    GenderEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static GenderEnum of(char value) {
        return switch (value) {
            case 'H' -> GenderEnum.MALE;
            case 'M' -> GenderEnum.FEMALE;
            case 'X' -> GenderEnum.NOT_BINARY;
            default -> throw new IllegalArgumentException("Invalid character for GenderEnum");
        };
    }
}
