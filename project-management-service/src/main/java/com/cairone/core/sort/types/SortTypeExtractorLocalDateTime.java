package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.function.Function;

public class SortTypeExtractorLocalDateTime<T> extends SortTypeExtractorAbstract<T, LocalDateTime> {

    @Override
    protected Function<T, LocalDateTime> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return LocalDateTime.parse(getterMethod.invoke(obj).toString());
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw throwException("Error working with sort field of type LocalDate", ex);
            }
        };
    }
}

