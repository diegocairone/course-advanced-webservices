package com.cairone.rest.request;

import com.cairone.core.form.CustomerForm;
import com.cairone.vo.Address;
import lombok.Data;

@Data
public class CustomerRequest implements CustomerForm {

    private String name;
    private String description;
    private Address mainLocation;
    private String cityId;
    private String phone;

}
