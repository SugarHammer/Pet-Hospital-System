package com.fjp.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BeanUtils {
    /**
     * 复制相同字段
     * @param obj 要复制的对象
     * @param tClass 要转换的类型
     * @param <T> 要转换的类型
     * @return 转换后的对象
     */
    public static <T> T copy(Object obj, Class<T> tClass) {
        try {
            Class<?> aClass = obj.getClass();
            Field[] afields = getAllFields(aClass);
            Field[] tfields = getAllFields(tClass);
            T t = tClass.newInstance();
            for (Field afield : afields) {
                for (Field tfield : tfields) {
                    if (afield.getName().equals(tfield.getName())) {
                        afield.setAccessible(true);
                        tfield.setAccessible(true);
                        tfield.set(t, afield.get(obj));
                    }
                }
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取本类及其父类的属性的方法
     * @param clazz 当前类对象
     * @return 字段数组
     */
    public static Field[] getAllFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null){
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        return fieldList.toArray(fields);
    }
}
