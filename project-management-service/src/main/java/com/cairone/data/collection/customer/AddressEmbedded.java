package com.cairone.data.collection.customer;

import com.cairone.vo.enums.StreetType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder(setterPrefix = "with")
public class AddressEmbedded {

    private String number;
    private String street;
    private StreetType type;
    private String apartment;
    private String zipCode;

}
