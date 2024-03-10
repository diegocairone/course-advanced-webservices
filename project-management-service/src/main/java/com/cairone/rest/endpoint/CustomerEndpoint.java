package com.cairone.rest.endpoint;

import com.cairone.core.resource.CustomerResource;
import com.cairone.rest.request.CustomerRequest;
import com.cairone.rest.resource.MultiErrorResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Customer", description = "Customers")
@RequestMapping("/api/customers")
public interface CustomerEndpoint {

    @PostMapping
    @Operation(summary = "Create a new customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created",
                    content = @Content(schema = @Schema(implementation = CustomerResource.class)),
                    headers = { @Header(
                            name = "Location",
                            description = "The URL to retrieve the created customer",
                            schema = @Schema(type = "string"))
                    }
            ),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class))
            ),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))
            )
    })
    ResponseEntity<CustomerResource> create(@Valid @RequestBody CustomerRequest request);
}
