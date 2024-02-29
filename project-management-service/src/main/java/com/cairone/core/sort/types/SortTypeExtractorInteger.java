package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class SortTypeExtractorInteger<T> extends SortTypeExtractorAbstract<T, Integer> {

    @Override
    protected Function<T, Integer> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return Integer.valueOf(getterMethod.invoke(obj).toString());
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw throwException("Error working with sort field of type LocalDate", ex);
            }
        };
    }
}
