package com.cairone.core.resource;

import com.cairone.vo.Address;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class CustomerResource {

    private final String id;
    private final String name;
    private final String description;
    private final Address mainLocation;
    private final CityResource city;
    private final String phone;

}
