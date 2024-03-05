package com.cairone.data.collection;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
@Document(collection = "employee-cv")
public class EmployeeCvDoc {

    @Id
    private UUID id;
    private String employeeNames;
    private String employeeFamilyNames;
    private UUID createdBy;
    private LocalDateTime createdOn;
    private LocalDateTime lastUpdated;
    private UUID updatedBy;
    private Map<String, Object> employeeData;

    @Builder(setterPrefix = "with")
    public EmployeeCvDoc(UUID id, String employeeNames, String employeeFamilyNames, UUID createdBy) {
        this.id = id;
        this.employeeNames = employeeNames;
        this.employeeFamilyNames = employeeFamilyNames;
        this.createdBy = createdBy;
        this.createdOn = LocalDateTime.now();
        this.updatedBy = createdBy;
        this.lastUpdated = null;
        this.employeeData = new HashMap<>();
    }
}
