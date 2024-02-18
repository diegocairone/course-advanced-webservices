package com.cairone.rest.endpoint;

import com.cairone.rest.resource.MultiErrorResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Employees CVs")
@RequestMapping("/api/employees/{id}/cv")
public interface EmployeeCvEndpoint {

    @PostMapping
    @Operation(summary = "Upload employee CV", description = "Upload employee CV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee CV uploaded", headers = {
                    @Header(name = "Location", description = "The URL to retrieve the uploaded CV",
                            schema = @Schema(type = "string"))
            }, content = @Content(schema = @Schema(implementation = LinkedHashMap.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class)))
    })
    ResponseEntity<Map<String, Object>> uploadCv(
            @PathVariable("id") UUID id,
            @RequestBody @Schema(implementation = LinkedHashMap.class) Map<String, Object> employeeData);

    @PutMapping
    @Operation(summary = "Replace employee CV", description = "Replace employee CV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee CV replaced", headers = {
                    @Header(name = "Location", description = "The URL to retrieve the replaced CV",
                            schema = @Schema(type = "string"))
            }, content = @Content(schema = @Schema(implementation = LinkedHashMap.class))),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class)))
    })
    ResponseEntity<Map<String, Object>> replaceCv(
            @PathVariable("id") UUID id,
            @RequestBody @Schema(implementation = LinkedHashMap.class) Map<String, Object> employeeData);

    @GetMapping
    @Operation(summary = "Download employee CV", description = "Download employee CV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee CV downloaded"),
            @ApiResponse(responseCode = "204", description = "Employee does not have a CV"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Employee not found",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class)))
    })
    ResponseEntity<Map<String, Object>> downloadCv(@PathVariable("id") UUID id);

    @DeleteMapping
    @Operation(summary = "Delete employee CV", description = "Delete employee CV")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Employee CV deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid ID supplied",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Void.class)))
            ),
            @ApiResponse(responseCode = "404", description = "Employee or CV not found",
                    content = @Content(schema = @Schema(implementation = MultiErrorResource.class)))
    })
    ResponseEntity<Void> deleteCv(@PathVariable("id") UUID id);
}
