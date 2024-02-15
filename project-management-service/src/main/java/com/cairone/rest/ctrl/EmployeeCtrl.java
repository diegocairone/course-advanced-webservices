package com.cairone.rest.ctrl;


import com.cairone.core.resource.EmployeeResource;
import com.cairone.core.service.EmployeeService;
import com.cairone.error.AppServerException;
import com.cairone.core.exception.ResourceNotFoundException;
import com.cairone.rest.endpoint.EmployeeEndpoint;
import com.cairone.rest.request.EmployeeRequest;
import com.cairone.utils.MediaTypeToFileExtensionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class EmployeeCtrl implements EmployeeEndpoint {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<EmployeeResource> create(EmployeeRequest request) {
        EmployeeResource employeeResource = employeeService.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(employeeResource.getId())
                .toUri();
        return ResponseEntity.created(uri).body(employeeResource);
    }

    @Override
    public ResponseEntity<EmployeeResource> update(UUID id, EmployeeRequest request) {
        EmployeeResource employeeResource = employeeService.save(id, request);
        return ResponseEntity.ok(employeeResource);
    }

    @Override
    public ResponseEntity<EmployeeResource> modify(UUID id, Map<String, Object> request) {
        EmployeeResource employeeResource = employeeService.save(id, request);
        return ResponseEntity.ok(employeeResource);
    }

    @Override
    public ResponseEntity<EmployeeResource> getEmployeeById(UUID id, Boolean withAvatar) {
        return employeeService.findById(id, withAvatar)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName("Employee")
                        .withResourceId(id.toString())
                        .build());
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Page<EmployeeResource>> getPagedEmployees(Pageable pageable) {
        return ResponseEntity.ok(employeeService.findAll(pageable));
    }

    @Override
    public ResponseEntity<Void> uploadAvatar(UUID id, MultipartFile avatar) {
        return handlerForPostAndPutAvatar(id, avatar, true);
    }

    @Override
    public ResponseEntity<Void> replaceAvatar(UUID id, MultipartFile avatar) {
        return handlerForPostAndPutAvatar(id, avatar, false);
    }

    private ResponseEntity<Void> handlerForPostAndPutAvatar(UUID id, MultipartFile avatar, boolean isPost) {

        try {

            byte[] data = avatar.getBytes();
            var noContent = ResponseEntity.noContent();

            if (isPost) {
                URI uri = employeeService.uploadAvatar(id, data).toURI();
                noContent.headers(h -> h.setLocation(uri));
            } else {
                URI uri = employeeService.replaceAvatar(id, data).toURI();
                noContent.headers(h -> h.setLocation(uri));
            }

            return noContent.build();

        } catch (URISyntaxException e) {
            throw new AppServerException.Builder()
                    .withMessage("Error trying to create avatar URL")
                    .withTechnicalMessage(e.getMessage())
                    .withCause(e)
                    .build();
        } catch (IOException e) {
            throw new AppServerException.Builder()
                    .withMessage("Error trying to read avatar file")
                    .withTechnicalMessage(e.getMessage())
                    .withCause(e)
                    .build();
        }
    }

    @Override
    public ResponseEntity<Resource> downloadAvatar(UUID id) {
        return employeeService.downloadAvatar(id)
                .map(contentHolder -> {
                    String contentType = contentHolder.contentMetadata().contentType();
                    MediaType mediaType = MediaType.parseMediaType(contentType);
                    String extension = MediaTypeToFileExtensionUtil.getExtension(mediaType);
                    return ResponseEntity.ok()
                            .contentType(mediaType)
                            .header(
                                    HttpHeaders.CONTENT_DISPOSITION,
                                    "inline;filename=" + id.toString() + extension)
                            .body(contentHolder.resource());
                })
                .orElseThrow(() -> ResourceNotFoundException.builder()
                        .withResourceName("Employee")
                        .withResourceId(id.toString())
                        .build());
    }

    @Override
    public ResponseEntity<Void> removeAvatar(UUID id) {
        employeeService.removeAvatar(id);
        return ResponseEntity.noContent().build();
    }
}
