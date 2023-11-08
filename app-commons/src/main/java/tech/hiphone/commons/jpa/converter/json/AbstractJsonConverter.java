package tech.hiphone.commons.jpa.converter.json;

import javax.persistence.AttributeConverter;

import tech.hiphone.commons.utils.GenericTypeUtil;
import tech.hiphone.commons.utils.JsonUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractJsonConverter<T> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return JsonUtil.toJsonString(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        Class<? extends AbstractJsonConverter> thisClass = this.getClass();
        Class<T> attributeClass = GenericTypeUtil.getGenericSuperclassType(thisClass);
        return JsonUtil.readValue(dbData, attributeClass);
    }

}
