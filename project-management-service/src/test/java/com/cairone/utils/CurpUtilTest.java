package com.cairone.utils;

import com.cairone.vo.enums.GenderEnum;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurpUtilTest {

    @Test
    void givenValidCurp_whenIsAMan_thenOk() {
        assertTrue(CurpUtil.validateCurp("PEGJ850315HJCRRN07"));
    }

    @Test
    void givenValidCurp_whenIsAWoman_thenOk() {
        assertTrue(CurpUtil.validateCurp("PEGJ850315MJCRRN07"));
    }

    @Test
    void givenValidCurp_whenGetBirthDatePreviousCenturyFromCurp_thenOk() {
        assertEquals("1985-03-15", CurpUtil.getBirthDateFromCurp("PEGJ850315HJCRRN07").toString());
    }

    @Test
    void givenValidCurp_whenGetBirthDateThisCenturyFromCurp_thenOk() {
        assertEquals("2020-03-15", CurpUtil.getBirthDateFromCurp("PEGJ200315HJCRRN07").toString());
    }

    @Test
    void givenValidCurp_whenGenderFromCurp_thenOk() {
        assertEquals(GenderEnum.MALE, CurpUtil.getGenderFromCurp("PEGJ200315HJCRRN07"));
    }
}