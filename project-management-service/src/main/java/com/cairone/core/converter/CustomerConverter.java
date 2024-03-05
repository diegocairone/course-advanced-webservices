package com.cairone.core.converter;

import com.cairone.core.resource.CityResource;
import com.cairone.core.resource.CountryResource;
import com.cairone.core.resource.CustomerResource;
import com.cairone.core.resource.StateResource;
import com.cairone.data.collection.CityDoc;
import com.cairone.data.collection.CustomerDoc;
import com.cairone.vo.Address;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter implements Converter<CustomerDoc, CustomerResource> {

    @Override
    public CustomerResource convert(CustomerDoc customerDoc) {

        Address address = Address.builder()
                .withNumber(customerDoc.getMainLocation().getNumber())
                .withStreet(customerDoc.getMainLocation().getStreet())
                .withType(customerDoc.getMainLocation().getType())
                .withApartment(customerDoc.getMainLocation().getApartment())
                .withZipCode(customerDoc.getMainLocation().getZipCode())
                .build();

        CustomerResource.CustomerResourceBuilder builder = CustomerResource.builder()
                .withId(customerDoc.getId().toHexString())
                .withName(customerDoc.getName())
                .withDescription(customerDoc.getDescription())
                .withMainLocation(address)
                .withPhone(customerDoc.getPhone());

        CityDoc cityDoc = customerDoc.getCity();

        CityResource.CityResourceBuilder cityResourceBuilder = CityResource.builder()
                .withId(cityDoc.getId().toHexString())
                .withName(cityDoc.getName());

        if (cityDoc.getState() != null) {

            StateResource.StateResourceBuilder stateResourceBuilder = StateResource.builder()
                    .withName(cityDoc.getState().getName());

            if (cityDoc.getState().getCountry() != null) {
                CountryResource countryResource = CountryResource.builder()
                        .withName(cityDoc.getState().getCountry().getName())
                        .build();
                stateResourceBuilder.withCountry(countryResource);
            }

            StateResource stateResource = stateResourceBuilder.build();
            cityResourceBuilder.withState(stateResource);
        }

        return builder.withCity(cityResourceBuilder.build()).build();
    }
}
