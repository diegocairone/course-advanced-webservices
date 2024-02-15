package com.cairone.core.service;

import com.cairone.core.converter.EmployeeConverter;
import com.cairone.core.form.EmployeeForm;
import com.cairone.core.resource.EmployeeResource;
import com.cairone.data.db.domain.EmployeeEntity;
import com.cairone.data.db.repository.EmployeeRepository;
import com.cairone.data.storage.ContentStorage;
import com.cairone.error.AppClientException;
import com.cairone.core.exception.ResourceNotFoundException;
import com.cairone.utils.CurpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private static final String RESOURCE_NAME = "Employee";

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
        return employeeRepository.findAll(pageable).map(employeeConverter::convert);
    }

    public EmployeeResource save(EmployeeForm employeeForm) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        return doSave(employeeEntity, employeeForm);
    }

    public EmployeeResource save(UUID id, EmployeeForm employeeForm) {
        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());
        return doSave(employeeEntity, employeeForm);
    }

    public EmployeeResource save(UUID id, Map<String, Object> request) {

        EmployeeEntity employeeEntity = employeeRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName(RESOURCE_NAME)
                        .withResourceId(id.toString())
                        .build());

        if (request.containsKey("name")) {
            if (request.get("name").toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Name cannot be empty");
            }
            employeeEntity.setName(request.get("name").toString().trim().toUpperCase());
        }
        if (request.containsKey("familyName")) {
            if (request.get("familyName").toString().trim().isEmpty()) {
                throw new IllegalArgumentException("Family name cannot be empty");
            }
            employeeEntity.setFamilyName(request.get("familyName").toString().trim().toUpperCase());
        }
        if (request.containsKey("curp")) {
            if (request.get("curp").toString().trim().isEmpty()) {
                throw new IllegalArgumentException("CURP cannot be empty");
            } else if (!CurpUtil.validateCurp(request.get("curp").toString().trim())) {
                throw new IllegalArgumentException("CURP is not valid");
            }
            employeeEntity.setCurp(request.get("curp").toString().trim());
            employeeEntity.setBirthDate(CurpUtil.getBirthDateFromCurp(request.get("curp").toString().trim()));
            employeeEntity.setGender(CurpUtil.getGenderFromCurp(request.get("curp").toString()));
        }

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

}
