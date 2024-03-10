package com.cairone.data.collection;

import com.cairone.data.collection.embedded.AddressEmbedded;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Document("customers")
public class CustomerDoc extends AuditableDocument {

    @Id
    @EqualsAndHashCode.Include
    @Field(targetType = FieldType.OBJECT_ID)
    private ObjectId id;
    @Field(targetType = FieldType.STRING)
    private String name;
    @Field(targetType = FieldType.STRING)
    private String description;
    @Field(name = "main_location")
    private AddressEmbedded mainLocation;
    @DocumentReference(collection = "cities")
    private CityDoc city;
    @Field(targetType = FieldType.STRING)
    private String phone;

    @Builder(setterPrefix = "with")
    public CustomerDoc(String name, String description, AddressEmbedded mainLocation, CityDoc city, String phone, UUID createdBy) {
        super(createdBy, LocalDateTime.now(), createdBy, LocalDateTime.now());
        this.name = name;
        this.description = description;
        this.mainLocation = mainLocation;
        this.city = city;
        this.phone = phone;
    }
}
