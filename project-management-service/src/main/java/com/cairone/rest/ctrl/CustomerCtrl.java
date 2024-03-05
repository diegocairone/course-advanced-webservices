package com.cairone.rest.ctrl;

import com.cairone.core.resource.CustomerResource;
import com.cairone.core.service.CustomerService;
import com.cairone.rest.endpoint.CustomerEndpoint;
import com.cairone.rest.request.CustomerRequest;
import com.cairone.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CustomerCtrl implements CustomerEndpoint {

    private final CustomerService customerService;

    @Override
    public ResponseEntity<CustomerResource> create(CustomerRequest request) {
        UUID createdBy = UUID.randomUUID();
        CustomerResource customerResource = customerService.save(request, createdBy);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerResource.getId())
                .toUri();
        return ResponseEntity.created(uri).body(customerResource);
    }

}
