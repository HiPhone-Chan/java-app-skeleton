package tech.hiphone.commons.jpa.converter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.persistence.AttributeConverter;

import tech.hiphone.commons.utils.JsonUtil;

public class JsonConverter<T> implements AttributeConverter<T, String> {

    @Override
    public String convertToDatabaseColumn(T attribute) {
        return JsonUtil.toJsonString(attribute);
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        return JsonUtil.readValue(dbData, getAttributeClass());
    }

    // 获取泛型类
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected Class<T> getAttributeClass() {
        Class<? extends JsonConverter> thisClass = this.getClass();
        ParameterizedType genericSuperclassType = (ParameterizedType) thisClass.getGenericSuperclass();
        Type genericType = genericSuperclassType.getActualTypeArguments()[0];
        return (Class<T>) genericType;
    }

}
