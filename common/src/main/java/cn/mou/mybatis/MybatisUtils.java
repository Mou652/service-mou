package cn.mou.mybatis;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.mou.excel.ExcelUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MybatisUtils常用工具类
 *
 * @author: mou
 * @date: 2020/2/13
 */
public class MybatisUtils {

    /***
     * 驼峰命名转为下划线命名
     *
     * @param str 驼峰命名的字符串
     */

    public static String humpToUnderline(String str) {
        StringBuilder sb = new StringBuilder(str);
        int temp = 0;
        if (!str.contains("_")) {
            for (int i = 0; i < str.length(); i++) {
                if (i > 0 && Character.isUpperCase(str.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 构建批量插入语句 insert 语句
     *
     * @param clazz        实体类class
     * @param beanList     要插入的带值的对象集合
     * @param ignoreFields 需要忽略的属性名集合 - 实体属性，不是数据库字段
     */
    public static <T> String getInsertString(Class<T> clazz, List<T> beanList, String... ignoreFields) {
        StringBuilder insertSql = new StringBuilder();
        String className = clazz.getSimpleName();
        insertSql.append("insert into ").append(ExcelUtils.humpToUnderline(className)).append("(");

        Field[] allFields = FieldUtils.getAllFields(clazz);

        // 构建字段
        StringBuilder columnSql = new StringBuilder();
        for (Field field : allFields) {
            // 构建非static的属性
            String name = field.getName();

            if (!isIgnore(ignoreFields, name)) {
                if (!Modifier.isStatic(field.getModifiers())) {
                    columnSql.append(ExcelUtils.humpToUnderline(name)).append(",");
                }
            }
        }

        insertSql.append(StrUtil.removeSuffix(columnSql.toString(), ",")).append(")").append(" values ");

        // 循环构建参数
        for (T t : beanList) {
            StringBuilder paramsSql = new StringBuilder();
            paramsSql.append("(");

            // 根据字段构建参数
            for (Field field : allFields) {
                // 构建非static的属性
                String name = field.getName();

                if (!isIgnore(ignoreFields, name)) {
                    if (!Modifier.isStatic(field.getModifiers())) {
                        Object fieldValue = ReflectUtil.getFieldValue(t, field.getName());

                        /* 根据格式转换数据 */
                        if (fieldValue instanceof String) {
                            paramsSql.append("'").append(ReflectUtil.getFieldValue(t, name)).append("',");
                        } else if (fieldValue instanceof Enum) {
                            paramsSql.append("'").append(ReflectUtil.getFieldValue(t, name)).append("',");
                        } else if (fieldValue instanceof Date) {
                            Date dateValue = (Date) ReflectUtil.getFieldValue(t, name);
                            paramsSql.append("'").append(getDateFormat(dateValue, "yyyy-MM-dd HH:mm:ss")).append("',");
                        } else {
                            paramsSql.append(ReflectUtil.getFieldValue(t, name)).append(",");
                        }
                    }
                }
            }

            insertSql.append(StrUtil.removeSuffix(paramsSql.toString(), ",")).append(")").append(",");
        }

        return StrUtil.removeSuffix(insertSql.toString(), ",");
    }

    /**
     * 是否忽略
     *
     * @param ignoreFields 需要忽略的属性名集合 - 实体属性，不是数据库字段
     * @param fieldName    属性名
     */
    private static boolean isIgnore(String[] ignoreFields, String fieldName) {
        boolean ignore = false;
        if (ArrayUtil.isNotEmpty(ignoreFields)) {
            // 简单的循环是最快的效率
            for (String ignoreField : ignoreFields) {
                if (ignoreField.equals(fieldName)) {
                    ignore = true;
                    break;
                }
            }
        }
        return ignore;
    }

    public static String getDateFormat(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

}
