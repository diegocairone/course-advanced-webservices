package com.cairone.core.sort;

import com.cairone.core.resource.EmployeeResource;
import com.cairone.vo.enums.GenderEnum;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SortUtilsTest {

    @Test
    void whenSorted_thenGetComparator() {

        EmployeeResource employee1 = EmployeeResource.builder()
                .withId(UUID.randomUUID())
                .withName("Bob")
                .withFamilyName("Doe")
                .withCurp("DOEJ123456")
                .withBirthDate(LocalDate.of(2024, 01, 02))
                .withGender(GenderEnum.MALE)
                .build();

        EmployeeResource employee2 = EmployeeResource.builder()
                .withId(UUID.randomUUID())
                .withName("John")
                .withFamilyName("Doe")
                .withCurp("DOEJ123456")
                .withBirthDate(LocalDate.of(2024, 01, 01))
                .withGender(GenderEnum.MALE)
                .build();

        EmployeeResource employee3 = EmployeeResource.builder()
                .withId(UUID.randomUUID())
                .withName("Alice")
                .withFamilyName("Doe")
                .withCurp("DOEJ123456")
                .withBirthDate(LocalDate.of(2024, 01, 01))
                .withGender(GenderEnum.MALE)
                .build();

        List<EmployeeResource> employees = List.of(employee1, employee2, employee3);

        Sort sort = Sort.by(
                Sort.Order.asc("birthDate"),
                Sort.Order.asc("name")
        );

        SortUtils sortUtils = new SortUtils();

        Comparator<EmployeeResource> comparator = sortUtils.getComparator(sort, EmployeeResource.class)
                .orElseGet(()  -> Comparator.comparing(EmployeeResource::getId));
        assertNotNull(comparator);

        List<EmployeeResource> sorted = employees.stream().sorted(comparator).toList();
        assertEquals(employees.get(0), sorted.get(2));
        assertEquals(employees.get(1), sorted.get(1));
        assertEquals(employees.get(2), sorted.get(0));
    }

    @Test
    void whenUnSorted_thenGetComparator() {

        SortUtils sortUtils = new SortUtils();

        Optional<Comparator<EmployeeResource>> comparator = sortUtils.getComparator(
                Sort.unsorted(), EmployeeResource.class);
        Assertions.assertThat(comparator).isEmpty();
    }
}