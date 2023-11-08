package tech.hiphone.commons.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings({ "rawtypes" })
public final class GenericTypeUtil {

    public static Class getGenericSuperclassType(Class clz) {
        return getGenericSuperclassType(clz, 0);
    }

    public static Class getGenericSuperclassType(Class clz, int pos) {
        ParameterizedType genericSuperclassType = (ParameterizedType) clz.getGenericSuperclass();
        Type genericType = genericSuperclassType.getActualTypeArguments()[pos];
        return (Class) genericType;
    }

}
