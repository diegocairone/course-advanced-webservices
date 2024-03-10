package com.cairone.data.collection;

import com.cairone.data.collection.embedded.AddressEmbedded;
import lombok.Builder;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Document("customers")
public class CustomerDoc {

    @Id
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
    @Field(name = "created_by", targetType = FieldType.STRING)
    private UUID createdBy;
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    private LocalDateTime createdAt;
    @Field(name = "updated_by", targetType = FieldType.STRING)
    private UUID updatedBy;
    @Field(name = "last_updated", targetType = FieldType.DATE_TIME)
    private LocalDateTime lastUpdated;

    @Builder(setterPrefix = "with")
    public CustomerDoc(String name, String description, AddressEmbedded mainLocation, CityDoc city, String phone, UUID createdBy) {
        this.name = name;
        this.description = description;
        this.mainLocation = mainLocation;
        this.city = city;
        this.phone = phone;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
        this.updatedBy = createdBy;
        this.lastUpdated = LocalDateTime.now();
    }
}
