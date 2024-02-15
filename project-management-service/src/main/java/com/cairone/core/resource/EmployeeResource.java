package com.cairone.core.resource;

import com.cairone.vo.enums.GenderEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.net.URL;
import java.util.UUID;

@Value
@Builder(setterPrefix = "with")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeResource {

    private final UUID id;
    private final String name;
    private final String familyName;
    private final String curp;
    private final String birthDate;
    private final GenderEnum gender;
    private final URL avatarUrl;

}
