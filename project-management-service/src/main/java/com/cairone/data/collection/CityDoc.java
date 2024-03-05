package com.cairone.data.collection;

import com.cairone.data.collection.embedded.StateEmbedded;
import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Value
@Builder(setterPrefix = "with")
@Document(collection = "cities")
public class CityDoc {

    @Id
    private final ObjectId id;
    private final String name;
    private final StateEmbedded state;

}
