package com.cairone.core.sort;

import com.cairone.error.AppServerException;
import org.springframework.data.domain.Sort;

import java.util.Comparator;
import java.util.function.Function;

public abstract class SortTypeExtractorAbstract<T, U extends Comparable<? super U>> {

    public Comparator<T> getComparator(Sort.Order order, String getterName) {
        Function<T, U> keyExtractor = getKeyExtractor(getterName);
        Comparator<T> comparator = Comparator.comparing(keyExtractor);
        if (order.getDirection().isDescending()) {
            return comparator.reversed();
        }
        return comparator;
    }

    protected abstract Function<T, U> getKeyExtractor(String getterName);

    protected AppServerException throwException(String message, Throwable cause) {
        return new AppServerException.Builder()
                .withMessage(message)
                .withTechnicalMessage(cause.getMessage())
                .withCause(cause)
                .build();
    }
}
