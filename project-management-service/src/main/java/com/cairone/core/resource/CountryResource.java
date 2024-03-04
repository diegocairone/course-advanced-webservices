package com.cairone.core.resource;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class CountryResource {

    private final String name;

}
