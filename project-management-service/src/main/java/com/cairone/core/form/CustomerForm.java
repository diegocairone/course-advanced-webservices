package com.cairone.core.form;

import com.cairone.vo.enums.StreetType;

public interface CustomerForm {

    public String getName();
    public String getDescription();
    public String getAddressNumber();
    public String getAddressStreet();
    public StreetType getAddressType();
    public String getAddressApartment();
    public String getAddressZipCode();
    public String getCityId();
    public String getPhone();

}
