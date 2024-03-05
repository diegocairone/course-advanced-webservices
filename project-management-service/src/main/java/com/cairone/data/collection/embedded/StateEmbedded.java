package com.cairone.data.collection.embedded;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(setterPrefix = "with")
public class StateEmbedded {

    private final String name;
    private final CountryEmbedded country;

}
