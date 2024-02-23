package com.cairone.core.sort.types;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

public class SortTypeExtractorString<T> extends SortTypeExtractorAbstract<T, String> {

    @Override
    protected Function<T, String> getKeyExtractor(String getterName) {
        return obj -> {
            try {
                Method getterMethod = obj.getClass().getMethod(getterName);
                return getterMethod.invoke(obj).toString();
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
                throw throwException("Error working with sort field of type String", ex);
            }
        };
    }
}
