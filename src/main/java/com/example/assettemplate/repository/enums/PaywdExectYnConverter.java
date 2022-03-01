package com.example.assettemplate.repository.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class PaywdExectYnConverter implements AttributeConverter<PaywdExectYn, String> {

    @Override
    public String convertToDatabaseColumn(PaywdExectYn attribute) {
        return attribute.getLegacyCode();
    }

    @Override
    public PaywdExectYn convertToEntityAttribute(String dbData) {
        return PaywdExectYn.ofLegacyCode(dbData);
    }
}
