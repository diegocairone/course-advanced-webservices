package com.cairone.vo.enums;

public enum GenderEnum {
    MALE('M'),
    FEMALE('F');

    char value;

    GenderEnum(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public static GenderEnum of(char value) {
        return value == 'M' ? GenderEnum.MALE : GenderEnum.FEMALE;
    }
}
