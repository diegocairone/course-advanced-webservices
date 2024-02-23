package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.function.Function;

public class SortTypeExtractorBigDecimal<T> extends SortTypeExtractorAbstract<T, BigDecimal> {

    @Override
    protected Function<T, BigDecimal> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return new BigDecimal(getterMethod.invoke(obj).toString());
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw throwException("Error working with sort field of type LocalDate", ex);
            }
        };
    }
}
