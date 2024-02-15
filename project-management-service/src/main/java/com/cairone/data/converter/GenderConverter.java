package com.cairone.data.converter;

import com.cairone.vo.enums.GenderEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class GenderConverter implements AttributeConverter<GenderEnum, Character> {

    @Override
    public Character convertToDatabaseColumn(GenderEnum genderEnum) {
        return genderEnum == null ? null : genderEnum.getValue();
    }

    @Override
    public GenderEnum convertToEntityAttribute(Character character) {
        return character == null ? null : GenderEnum.of(character);
    }
}
