package br.com.nivlabs.gp.models.converter;

import javax.persistence.AttributeConverter;

public class IntegerToBooleanConverver implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null) {
            if (attribute) {
                return Integer.valueOf(1);
            } else {
                return Integer.valueOf(0);
            }
        }
        return Integer.valueOf(0);
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData != null) {
            return dbData.equals(1);
        }
        return false;
    }

}
