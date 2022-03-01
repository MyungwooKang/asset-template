package com.example.assettemplate.repository.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class AcdnHdlStatCdConverter implements AttributeConverter<AcdnHdlStatCd, String> {

    @Override
    public String convertToDatabaseColumn(AcdnHdlStatCd attribute) {
        return attribute.getLegacyCode();
    }

    @Override
    public AcdnHdlStatCd convertToEntityAttribute(String dbData) {
        return AcdnHdlStatCd.ofLegacyCode(dbData);
    }
}
