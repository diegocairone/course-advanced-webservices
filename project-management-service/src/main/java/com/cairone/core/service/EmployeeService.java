package com.cairone.core.service;

import com.cairone.core.converter.EmployeeConverter;
import com.cairone.core.exception.ResourceNotFoundException;
import com.cairone.core.form.EmployeeForm;
import com.cairone.core.resource.EmployeeResource;
import com.cairone.core.sort.SortUtils;
import com.cairone.data.db.domain.EmployeeEntity;
import com.cairone.data.db.repository.EmployeeRepository;
import com.cairone.data.docs.domain.EmployeeCvDoc;
import com.cairone.data.docs.repository.EmployeeCvRepository;
import com.cairone.data.storage.ContentStorage;
import com.cairone.error.AppClientException;
import com.cairone.rest.request.EmployeeRequest;
import com.cairone.utils.CurpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final String RESOURCE_NAME = "Employee";

    private final EmployeeCvRepository employeeCvRepository;
    private final EmployeeRepository employeeRepository;
    private final EmployeeConverter employeeConverter;
    private final ContentStorage contentStorage;

    public Optional<EmployeeResource> findById(UUID id) {
        return findById(id, false);
    }

    public Optional<EmployeeResource> findById(UUID id, boolean withAvatar) {
        return employeeRepository.findById(id)
                .map(employee -> employeeConverter.convert(employee, withAvatar));
    }

    public Page<EmployeeResource> findAll(Pageable pageable) {

        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        Page<EmployeeEntity> employeePage = employeeRepository.findAll(pageRequest);

        if (pageable.getSort().isUnsorted()) {
            return employeePage.map(employeeConverter::convert);
        }

        SortUtils sortUtils = new SortUtils();
        Comparator<EmployeeResource> comparator = sortUtils.getComparator(pageable.getSort(), EmployeeResource.class)
                .orElseGet(() -> Comparator.comparing(EmployeeResource::getId));

        List<EmployeeResource> sorted = employeePage.stream()
                .map(employeeConverter::convert)
                .sorted(comparator)
                .toList();

        return new PageImpl<>(sorted, pageable, employeePage.getTotalElements());
    }

    public EmployeeResource save(EmployeeForm employeeForm, UUID createdBy) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setCreatedOn(LocalDateTime.now());
        employeeEntity.setLastUpdated(LocalDateTime.now());
        employeeEntity.setCreatedBy(createdBy);
        employeeEntity.setUpdatedBy(createdBy);
        return doSave(employeeEntity, employeeForm);
    }

    public EmployeeResource save(UUID id, EmployeeForm employeeForm, UUID updatedBy) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());
        employeeEntity.setLastUpdated(LocalDateTime.now());
        employeeEntity.setUpdatedBy(updatedBy);
        return doSave(employeeEntity, employeeForm);
    }

    public EmployeeResource save(UUID id, Map<String, Object> request) {

        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());

        Class<EmployeeRequest> clazz = EmployeeRequest.class;
        Map<String, Field> fieldMap = Arrays.asList(clazz.getDeclaredFields())
                .stream()
                .collect(Collectors.toMap(Field::getName, field -> field));

        request.entrySet().forEach(entry -> {
            if (!fieldMap.containsKey(entry.getKey())) {
                throw new IllegalArgumentException("Invalid field: " + entry.getKey());
            }
            String fieldValue = entry.getValue().toString().trim();
            if (fieldValue.isEmpty()) {
                throw new IllegalArgumentException("Field " + entry.getKey() + " cannot be empty");
            }

            if (entry.getKey().equals("name")) {
                employeeEntity.setName(fieldValue.toUpperCase());
            } else if (entry.getKey().equals("familyName")) {
                employeeEntity.setFamilyName(fieldValue.toUpperCase());
            } else if (entry.getKey().equals("curp")) {
                if (!CurpUtil.validateCurp(fieldValue)) {
                    throw new IllegalArgumentException("CURP is not valid");
                }
                employeeEntity.setCurp(fieldValue);
                employeeEntity.setBirthDate(CurpUtil.getBirthDateFromCurp(fieldValue));
                employeeEntity.setGender(CurpUtil.getGenderFromCurp(fieldValue));
            }
        });

        EmployeeEntity saved = employeeRepository.save(employeeEntity);
        return employeeConverter.convert(saved);
    }

    private EmployeeResource doSave(EmployeeEntity employeeEntity, EmployeeForm employeeForm) {

        employeeEntity.setName(employeeForm.getName().trim().toUpperCase());
        employeeEntity.setFamilyName(employeeForm.getFamilyName().trim().toUpperCase());
        employeeEntity.setCurp(employeeForm.getCurp().trim());
        employeeEntity.setBirthDate(CurpUtil.getBirthDateFromCurp(employeeForm.getCurp().trim()));
        employeeEntity.setGender(CurpUtil.getGenderFromCurp(employeeForm.getCurp()));

        EmployeeEntity saved = employeeRepository.save(employeeEntity);
        return employeeConverter.convert(saved);
    }

    public void delete(UUID id) {
        employeeRepository.findById(id).ifPresentOrElse(employeeRepository::delete, () -> {
            throw ResourceNotFoundException.builder()
                    .withResourceName(RESOURCE_NAME)
                    .withResourceId(id.toString())
                    .build();
        });
    }

    public URL uploadAvatar(UUID id, byte[] avatar) {
        return handleAvatar(id, avatar, employeeEntity -> {
            if (contentStorage.existsById(id)) {
                throw new AppClientException("Avatar already exists for employee with ID %s", id);
            }
        });
    }

    public URL replaceAvatar(UUID id, byte[] avatar) {
        return handleAvatar(id, avatar, employeeEntity -> {
            if (contentStorage.nonExistsById(id)) {
                throw new AppClientException("Avatar does not exist for employee with ID %s", id);
            }
        });
    }

    public void removeAvatar(UUID id) {
        employeeRepository.findById(id)
                .ifPresentOrElse(
                        employeeEntity -> contentStorage.removeById(employeeEntity.getId()),
                        () -> {
                            throw ResourceNotFoundException.builder()
                                    .withResourceName(RESOURCE_NAME)
                                    .withResourceId(id.toString())
                                    .build();
                });
    }

    private URL handleAvatar(UUID id, byte[] avatar, Consumer<EmployeeEntity> consumer) {

        if (Objects.isNull(avatar)) {
            throw new AppClientException("Avatar image is empty!");
        }

        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());

        if (consumer != null) {
            consumer.accept(employeeEntity);
        }

        return contentStorage.uploadContent(
                employeeEntity.getId(),
                avatar,
                builder -> builder.key("employee-curp").value(employeeEntity.getCurp()));
    }

    public Optional<ContentStorage.ContentHolder> downloadAvatar(UUID id) {
        return employeeRepository.findById(id)
                .map(employeeEntity -> contentStorage.downloadContent(employeeEntity.getId()))
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());
    }

    public Optional<EmployeeCvDoc> findCvById(UUID id) {
        return employeeRepository.findById(id)
                .map(employeeEntity -> employeeCvRepository.findById(employeeEntity.getId())
                        .orElseThrow(() -> ResourceNotFoundException.builder()
                                .withResourceName("Employee CV")
                                .withResourceId(id.toString())
                                .build()));
    }

    public EmployeeCvDoc uploadNewCv(UUID id, Map<String, Object> employeeData) {
        return handleCv(id, employeeData, employeeEntity -> {
            if (employeeCvRepository.existsById(employeeEntity.getId())) {
                throw new AppClientException("Employee CV already exists for employee with ID %s", id);
            }
        });
    }

    public EmployeeCvDoc replaceExistingCv(UUID id, Map<String, Object> employeeData) {
        return handleCv(id, employeeData, employeeEntity -> {
            if (!employeeCvRepository.existsById(employeeEntity.getId())) {
                throw new AppClientException("Employee CV does not exist for employee with ID %s", id);
            }
        });
    }

    public void deleteCv(UUID id) {
        employeeRepository.findById(id).ifPresentOrElse(
                employeeEntity ->
                    employeeCvRepository.findById(employeeEntity.getId())
                            .ifPresentOrElse(
                                    employeeCvRepository::delete,
                                    () -> {
                                        throw ResourceNotFoundException.builder()
                                                .withResourceName("Employee CV")
                                                .withResourceId(id.toString())
                                                .build();
                                    }),
                () -> {
                    throw ResourceNotFoundException.builder()
                            .withResourceName(RESOURCE_NAME)
                            .withResourceId(id.toString())
                            .build();
                });
    }

    private EmployeeCvDoc handleCv(UUID id, Map<String, Object> employeeData, Consumer<EmployeeEntity> consumer) {

        if (Objects.isNull(employeeData) || employeeData.isEmpty()) {
            throw new AppClientException("Employee data is empty!");
        }

        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());

        // run logic to check if employee CV exists or not
        // when it exists, but I am creating, throw an exception
        // when it does not exist, but I am replacing, throw an exception
        if (consumer != null) {
            consumer.accept(employeeEntity);
        }

        EmployeeCvDoc employeeCvDoc = employeeCvRepository.findById(employeeEntity.getId())
                .orElseGet(() -> EmployeeCvDoc.builder()
                        .withId(employeeEntity.getId())
                        .withEmployeeNames(employeeEntity.getName())
                        .withEmployeeFamilyNames(employeeEntity.getFamilyName())
                        .build());

        employeeCvDoc.setLastUpdated(LocalDateTime.now());
        employeeData.forEach(employeeCvDoc.getEmployeeData()::put);

        return employeeCvRepository.save(employeeCvDoc);
    }
}
