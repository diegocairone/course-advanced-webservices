package com.cairone.core.resource;

import com.cairone.vo.Address;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(setterPrefix = "with")
public class CustomerResource {

    private final UUID id;
    private final String name;
    private final String description;
    private final Address mainLocation;
    private final CityResource city;

}
