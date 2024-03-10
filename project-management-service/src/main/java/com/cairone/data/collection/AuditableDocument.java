package com.cairone.data.collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class AuditableDocument {

    @Field(name = "created_by", targetType = FieldType.STRING)
    protected UUID createdBy;
    @Field(name = "created_at", targetType = FieldType.DATE_TIME)
    protected LocalDateTime createdAt;
    @Field(name = "updated_by", targetType = FieldType.STRING)
    protected UUID updatedBy;
    @Field(name = "last_updated", targetType = FieldType.DATE_TIME)
    protected LocalDateTime lastUpdated;

    protected AuditableDocument(UUID createdBy, LocalDateTime createdAt, UUID updatedBy, LocalDateTime lastUpdated) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedBy = updatedBy;
        this.lastUpdated = lastUpdated;
    }
}
