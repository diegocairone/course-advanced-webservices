package com.cairone.vo;

import com.cairone.vo.enums.StreetType;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class Address {

    private final String number;
    private final String street;
    private final StreetType type;
    private final String apartment;
    private final String zipCode;

}
