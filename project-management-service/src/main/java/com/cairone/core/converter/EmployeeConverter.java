package com.cairone.core.converter;

import com.cairone.core.resource.EmployeeResource;
import com.cairone.data.db.domain.EmployeeEntity;
import com.cairone.data.storage.ContentStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class EmployeeConverter implements Converter<EmployeeEntity, EmployeeResource> {

    private final ContentStorage contentStorage;

    @Override
    public EmployeeResource convert(EmployeeEntity source) {
        return convert(source, false);
    }

    public EmployeeResource convert(EmployeeEntity source, boolean includeAvatar) {
        URL avatarUrl = null;
        if (includeAvatar) {
            avatarUrl = contentStorage.getPresignedUrl(source.getId());
        }
        return EmployeeResource.builder()
                .withId(source.getId())
                .withName(source.getName())
                .withFamilyName(source.getFamilyName())
                .withCurp(source.getCurp())
                .withBirthDate(source.getBirthDate().toString())
                .withGender(source.getGender())
                .withAvatarUrl(avatarUrl)
                .build();
    }
}
