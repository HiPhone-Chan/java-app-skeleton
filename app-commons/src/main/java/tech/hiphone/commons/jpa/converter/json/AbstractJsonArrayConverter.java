package tech.hiphone.commons.jpa.converter.json;

import java.util.List;

import javax.persistence.AttributeConverter;

import tech.hiphone.commons.utils.GenericTypeUtil;
import tech.hiphone.commons.utils.JsonUtil;

@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractJsonArrayConverter<T> extends AbstractJsonConverter<List<T>>
        implements AttributeConverter<List<T>, String> {

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        Class<? extends AbstractJsonArrayConverter> thisClass = this.getClass();
        Class<T> attributeClass = GenericTypeUtil.getGenericSuperclassType(thisClass);
        return JsonUtil.readArrayValue(dbData, attributeClass);
    }

}
