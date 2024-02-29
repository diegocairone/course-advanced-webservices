package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.function.Function;

public class SortTypeExtractorUuid<T> extends SortTypeExtractorAbstract<T, UUID> {

    @Override
    protected Function<T, UUID> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return UUID.fromString(getterMethod.invoke(obj).toString());
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException ex) {
                throw throwException("Error working with sort field of type LocalDate", ex);
            }
        };
    }
}
