package com.cairone.data.collection.embedded;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class CountryEmbedded {

    private final String name;
}
