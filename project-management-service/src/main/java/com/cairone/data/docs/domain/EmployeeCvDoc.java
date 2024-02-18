package com.cairone.data.docs.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private Map<String, Object> employeeData;

    @Builder(setterPrefix = "with")
    public EmployeeCvDoc(UUID id, String employeeNames, String employeeFamilyNames) {
        this.id = id;
        this.employeeNames = employeeNames;
        this.employeeFamilyNames = employeeFamilyNames;
        this.employeeData = new HashMap<>();
    }
}
