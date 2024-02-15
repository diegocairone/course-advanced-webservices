package com.cairone.rest.request;

import com.cairone.core.form.EmployeeForm;
import com.cairone.utils.CurpUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class EmployeeRequest implements EmployeeForm {

    @NotBlank
    private String name;
    @NotBlank
    private String familyName;
    @NotBlank
    @Pattern(regexp = CurpUtil.CURP_REGEX)
    private String curp;

}
