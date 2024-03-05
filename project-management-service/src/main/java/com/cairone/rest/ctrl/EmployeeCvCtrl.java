package com.cairone.rest.ctrl;

import com.cairone.core.service.EmployeeService;
import com.cairone.data.collection.EmployeeCvDoc;
import com.cairone.rest.endpoint.EmployeeCvEndpoint;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeCvCtrl implements EmployeeCvEndpoint {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<Map<String, Object>> uploadCv(UUID id, Map<String, Object> employeeData) {
        EmployeeCvDoc employeeCvDoc = employeeService.uploadNewCv(id, employeeData);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}/cv")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.created(uri).body(employeeCvDoc.getEmployeeData());
    }

    @Override
    public ResponseEntity<Map<String, Object>> replaceCv(UUID id, Map<String, Object> employeeData) {
        EmployeeCvDoc employeeCvDoc = employeeService.replaceExistingCv(id, employeeData);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}/cv")
                .buildAndExpand(id)
                .toUri();
        return ResponseEntity.ok().location(uri).body(employeeCvDoc.getEmployeeData());
    }

    @Override
    public ResponseEntity<Map<String, Object>> downloadCv(UUID id) {
        return employeeService.findCvById(id)
                .map(doc -> ResponseEntity.ok(doc.getEmployeeData()))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @Override
    public ResponseEntity<Void> deleteCv(UUID id) {
        employeeService.deleteCv(id);
        return ResponseEntity.noContent().build();
    }
}
