package tech.hiphone.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import tech.hiphone.commons.constants.ErrorCodeContants;
import tech.hiphone.commons.exceptioin.ServiceException;

@SuppressWarnings("unchecked")
public class BeanUtil {

    public static <T> T castObject(Object obj, Class<T> cls) {
        if (cls.equals(obj.getClass())) {
            return (T) obj;
        }
        if (obj instanceof String) {
            return JsonUtil.readValue((String) obj, cls);
        }
        try {
            return JsonUtil.convertValue(obj, cls);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ErrorCodeContants.DATA_FORMAT, "Format not right.");
        }
    }

    public static Object getFieldValue(Object obj, String fieldName) {
        String methodName = "get" + StringUtils.capitalize(fieldName);
        Object value = null;
        try {
            Method getMethod = obj.getClass().getMethod(methodName);
            value = getMethod.invoke(obj);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void setNotNullFieldValue(Object obj, String fieldName, Object value) {
        if (value == null) {
            return;
        }
        setFieldValue(obj, fieldName, value);
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) {
        String methodName = "set" + StringUtils.capitalize(fieldName);
        try {
            Method setMethod = obj.getClass().getMethod(methodName, value.getClass());
            setMethod.invoke(obj, value);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public static String getName(Field field) {
        String name = field.getName();

        JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
        if (jsonProperty != null) {
            name = jsonProperty.value();
        }
        return name;
    }

    public static Map<String, String> toMap(Object obj) {
        Map<String, String> map = new HashMap<>();
        if (obj instanceof Map) {
            Map<?, ?> objMap = (Map<?, ?>) obj;
            for (Object key : objMap.keySet()) {
                if (key != null) {
                    put(map, key.toString(), objMap.get(key));
                }
            }
        } else {
            Class<?> clz = obj.getClass();
            while (clz != Object.class) {
                for (Field field : clz.getDeclaredFields()) {
                    JsonIgnore jsonIgnore = field.getAnnotation(JsonIgnore.class);
                    if (jsonIgnore != null) {
                        continue;
                    }

                    String name = BeanUtil.getName(field);
                    String fieldName = field.getName();
                    Object value = BeanUtil.getFieldValue(obj, fieldName);
                    if (value != null) {
                        map.put(name, value.toString());
                    }
                }
                clz = clz.getSuperclass();
            }
        }
        return map;
    }

    public static Map<String, String> put(Map<String, String> map, String key, Object value) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            map.put(key, value.toString());
        }
        return map;
    }
}
