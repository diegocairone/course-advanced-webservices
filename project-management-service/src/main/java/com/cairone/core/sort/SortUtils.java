package com.cairone.core.sort;

import com.cairone.core.sort.types.*;
import com.cairone.error.AppClientException;
import com.cairone.error.AppServerException;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Streamable;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SortUtils {

    private Map<Type, SortTypeExtractorAbstract<?, ?>> typeMap;

    public SortUtils() {
        this.typeMap = new LinkedHashMap<>();
        this.typeMap.put(UUID.class, new SortTypeExtractorUuid<>());
        this.typeMap.put(Integer.class, new SortTypeExtractorInteger<>());
        this.typeMap.put(Long.class, new SortTypeExtractorLong<>());
        this.typeMap.put(String.class, new SortTypeExtractorString<>());
        this.typeMap.put(LocalDate.class, new SortTypeExtractorLocalDate<>());
        this.typeMap.put(LocalDateTime.class, new SortTypeExtractorLocalDateTime<>());
        this.typeMap.put(BigDecimal.class, new SortTypeExtractorBigDecimal<>());
    }

    public SortUtils addTypeExtractor(Type type, SortTypeExtractorAbstract<?, ?> extractor) {
        typeMap.put(type, extractor);
        return this;
    }

    public <T, R extends Comparable<? super R>> SortUtils addTypeExtractor(Type type, Function<T, R> function) {
        SortTypeExtractorAbstract<T, ?> extractor = new SortTypeExtractorAbstract<T, R>() {
            @Override
            protected Function<T, R> getKeyExtractor(String getterName) {
                return function;
            }
        };
        addTypeExtractor(type, extractor);
        return this;
    }

    public <T> Optional<Comparator<T>> getComparator(Sort sort, Class<T> clazz) {

        if (sort.isUnsorted()) {
            return Optional.empty();
        }

        Field[] fields = clazz.getDeclaredFields();
        Map<String, Field> fieldMap = List.of(fields)
                .stream()
                .collect(Collectors.toMap(Field::getName, field -> field));

        Streamable<Comparator<T>> comparators = sort.map(order -> {

            Field field = fieldMap.get(order.getProperty());
            if (field == null) {
                throw new AppClientException("Invalid sort field: %s", order.getProperty());
            }

            Type type = field.getGenericType();
            String getterName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

            SortTypeExtractorAbstract<T, ?> extractor = (SortTypeExtractorAbstract<T, ?>) typeMap.get(type);
            if (extractor != null) {
                return extractor.getComparator(order, getterName);
            }

            throw new AppServerException.Builder()
                    .withMessage("Error working with sort field: " + field.getName())
                    .withTechnicalMessage(
                            "Unsupported type for field: " + field.getName()
                                    + " of type: " + type.getTypeName()
                                    + " in class: " + clazz.getName())
                    .build();
        });

        return comparators.stream().reduce(Comparator::thenComparing);
    }
}
