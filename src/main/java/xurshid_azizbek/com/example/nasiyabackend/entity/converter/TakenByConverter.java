package xurshid_azizbek.com.example.nasiyabackend.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import xurshid_azizbek.com.example.nasiyabackend.entity.enums.TakenBy;

@Converter
public class TakenByConverter implements AttributeConverter<TakenBy, String> {

    @Override
    public String convertToDatabaseColumn(TakenBy attribute) {
        return attribute == null ? null : attribute.getLabel();
    }

    @Override
    public TakenBy convertToEntityAttribute(String dbData) {
        return dbData == null ? null : TakenBy.fromLabel(dbData);
    }
}