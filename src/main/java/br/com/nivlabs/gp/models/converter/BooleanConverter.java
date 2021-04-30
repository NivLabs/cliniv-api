package br.com.nivlabs.gp.models.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Conversor de tipo para persistência de Booleanos
 * 
 * @author viniciosarodrigues
 *
 */
@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Character> {

    @Override
    public Character convertToDatabaseColumn(Boolean attribute) {
        if (attribute != null) {
            if (attribute) {
                return 'S';
            } else {
                return 'N';
            }

        }
        return 'N';
    }

    @Override
    public Boolean convertToEntityAttribute(Character dbData) {
        if (dbData != null) {
            return dbData.equals('S');
        }
        return false;
    }

}