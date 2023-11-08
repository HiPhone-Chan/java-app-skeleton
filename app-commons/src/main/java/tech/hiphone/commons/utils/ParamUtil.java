package tech.hiphone.commons.utils;

import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

import tech.hiphone.commons.constants.CommonsConstants;

// 处理 name1=value1&name2=value2...格式

public final class ParamUtil {

    private static final Logger log = LoggerFactory.getLogger(ParamUtil.class);

    private ParamUtil() {
    }

    public static String toString(Object obj) {
        return toString(obj, true);
    }

    public static String toString(Object obj, boolean urlEncode) {
        StringBuilder sb = new StringBuilder();

        try {
            Map<String, String> map = BeanUtil.toMap(obj);

            for (String key : map.keySet()) {
                String value = map.get(key);
                if (urlEncode) {
                    value = URLEncoder.encode(value, CommonsConstants.CHARSET);
                }
                put(sb, key, value);
            }
        } catch (Exception e) {
            log.warn("toString failed", e);
        }
        return sb.toString();
    }

    // 按字母顺序排序的字符串
    public static String sortString(Object pojo) {
        Map<String, String> map = BeanUtil.toMap(pojo);
        Map<String, String> treeMap = new TreeMap<>(map);

        StringBuilder sb = new StringBuilder();
        for (String key : treeMap.keySet()) {
            put(sb, key, treeMap.get(key));
        }
        return sb.toString();
    }

    public static MultiValueMap<String, Object> toFormEntity(Object obj) {
        MultiValueMap<String, Object> formEntity = new LinkedMultiValueMap<>();
        try {
            if (obj instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) obj;
                for (Object key : map.keySet()) {
                    add(formEntity, key.toString(), map.get(key));
                }
            } else {
                Class<?> clz = obj.getClass();
                for (Field field : clz.getDeclaredFields()) {
                    String name = BeanUtil.getName(field);
                    String fieldName = field.getName();
                    Object value = BeanUtil.getFieldValue(obj, fieldName);
                    add(formEntity, name, value);
                }
            }
        } catch (Exception e) {
            log.warn("toString failed", e);
        }
        return formEntity;
    }

    public static <T> T toObject(String params, Class<T> clz) {
        Map<String, String> map = toMap(params);
        return BeanUtil.castObject(map, clz);
    }

    public static Map<String, String> toMap(String params) {
        Map<String, String> map = new HashMap<>();
        String[] keyValues = params.split("&");
        for (String keyValue : keyValues) {
            try {
                String[] pair = keyValue.split("=");
                map.put(pair[0], URLDecoder.decode(pair[1], CommonsConstants.CHARSET));
            } catch (Exception e) {
                log.warn("split & exception", e);
            }
        }
        return map;
    }

    private static MultiValueMap<String, Object> add(MultiValueMap<String, Object> form, String key, Object value) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            form.add(key, value);
        }
        return form;
    }

    private static StringBuilder put(StringBuilder sb, String key, String value) {
        if (!ObjectUtils.isEmpty(key) && !ObjectUtils.isEmpty(value)) {
            if (sb.length() > 0) {
                sb.append('&');
            }
            sb.append(key).append('=').append(value);
        }
        return sb;
    }

}
