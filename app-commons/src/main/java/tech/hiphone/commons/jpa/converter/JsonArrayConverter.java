package tech.hiphone.commons.jpa.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.AttributeConverter;

import tech.hiphone.commons.utils.JsonUtil;

public class JsonArrayConverter<T> implements AttributeConverter<List<T>, String> {

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        return JsonUtil.toJsonString(attribute);
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        return JsonUtil.readArrayValue(dbData, getAttributeClass());
    }

    // 获取泛型类
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Class<T> getAttributeClass() {
        Class<? extends JsonArrayConverter> thisClass = this.getClass();
        ParameterizedType genericSuperclassType = (ParameterizedType) thisClass.getGenericSuperclass();
        Type genericType = genericSuperclassType.getActualTypeArguments()[0];
        return (Class<T>) genericType;
    }

}
