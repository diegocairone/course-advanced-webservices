package com.cairone.rest.request;

import com.cairone.core.form.CustomerForm;
import com.cairone.vo.enums.StreetType;
import lombok.Data;

@Data
public class CustomerRequest implements CustomerForm {

    private String name;
    private String description;
    private final String addressNumber;
    private final String addressStreet;
    private final StreetType addressType;
    private final String addressApartment;
    private final String addressZipCode;
    private String cityId;
    private String phone;

}
