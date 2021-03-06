package com.mou.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实现自定义注解类规则读取与校验
 *
 * @author: mou
 * @date: 2019-08-07
 */
public class AnnotationChecker {
    private final static ConcurrentHashMap<String, Field[]> FIELDS_MAP = new ConcurrentHashMap<String, Field[]>();

    public AnnotationChecker() {
        super();
    }

    public static void checkParam(Object object) throws PostingException {
        Class<? extends Object> clazz = object.getClass();
        Field[] fields = null;
        if (FIELDS_MAP.containsKey(clazz.getName())) {
            fields = FIELDS_MAP.get(clazz.getName());
        }
        // getFields:获得某个类的所有的公共（public）的字段，包括父类;getDeclaredFields获得某个类的所有申明的字段，即包括public、private和proteced，但是不包括父类的申明字段
        else {
            fields = clazz.getDeclaredFields();
            FIELDS_MAP.put(clazz.getName(), fields);
        }
        for (Field field : fields) {
            synchronized (field) {
                field.setAccessible(true);
                check(field, object);
                field.setAccessible(false);
            }
        }
    }

    private static void check(Field field, Object object) throws PostingException {
        String description;
        Object value = null;
        CustomAnnotation ca = field.getAnnotation(CustomAnnotation.class);
        try {
            value = field.get(object);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (ca == null) {
            return;
        }
        // 如果没有标注description默认展示字段名称
        description = ca.description().equals("") ? field.getName() : ca.description();
        if (!ca.isNull()) {
            if (value == null || StringUtils.isEmpty(value.toString().trim())) {
                throw new PostingException(CommonPostCode.PARAM_LENGTH.getErrorCode(), description + " " + CommonPostCode.PARAM_NULL.getErrorMesage());
            }
        }
        if (value.toString().length() > ca.maxLength()) {
            throw new PostingException(CommonPostCode.PARAM_LENGTH.getErrorCode(), description + " " + CommonPostCode.PARAM_LENGTH.getErrorCode());
        }
    }
}
