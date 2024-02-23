package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class SortTypeExtractorLong<T> extends SortTypeExtractorAbstract<T, Long> {

    @Override
    protected Function<T, Long> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return Long.valueOf(getterMethod.invoke(obj).toString());
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw throwException("Error working with sort field of type LocalDate", ex);
            }
        };
    }
}
