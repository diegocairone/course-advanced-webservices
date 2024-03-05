package com.cairone.core.resource;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class CityResource {

    private final String id;
    private final String name;
    private final StateResource state;

}
