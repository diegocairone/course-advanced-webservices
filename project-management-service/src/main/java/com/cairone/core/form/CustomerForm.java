package com.cairone.core.form;

import com.cairone.vo.Address;

import java.util.UUID;

public interface CustomerForm {

    public String getName();
    public String getDescription();
    public Address getMainLocation();
    public String getCityId();
    public String getPhone();

}
