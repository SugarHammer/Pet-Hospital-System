package com.fjp.util;

import com.fjp.exceptions.BusinessException;
import com.github.pagehelper.Page;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GlobalUtils {
    /**
     * 检验插入数据字段是否重复，传入实体中只要一个属性与数据库的数据重复，就抛出异常
     * @param entity 要插入的数据
     * @param map 确认存在的字段名称及对应要抛出的异常
     * @param mapper 查询数据库
     * @param ignoreEntity 是否忽略当前这个实体，用于修改操作
     * @param <T> 传入数据类型
     */
    public static <T> void checkColumnExist(T entity, Map<String, BusinessException> map, Mapper<T> mapper, boolean ignoreEntity) {
        try {
            Class<?> clazz = entity.getClass();
            for (Map.Entry<String, BusinessException> entry : map.entrySet()) {
                String key = entry.getKey();
                BusinessException value = entry.getValue();
                Example example = new Example(clazz);
                Example.Criteria criteria = example.createCriteria();
                Field field = clazz.getDeclaredField(key);
                field.setAccessible(true);
                if (ignoreEntity) {
                    Field idField = getIdField(clazz);
                    criteria.andNotEqualTo("id", idField.get(entity));
                }
                criteria.andEqualTo(key, field.get(entity));
                List<T> list = mapper.selectByExample(example);
                if (!list.isEmpty()) {
                    throw value;
                }
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 检验数据id是否在数据库中存在
     * @param entity 要更新的数据
     * @param mapper 查询数据库
     * @param <T> 传入数据类型
     */
    public static <T> void checkExistId(T entity, Mapper<T> mapper, BusinessException exception) {
        try {
            Class<?> clazz = entity.getClass();
            Example example = new Example(clazz);
            Example.Criteria criteria = example.createCriteria();
            Field idField = getIdField(clazz);
            criteria.andEqualTo("id", idField.get(entity));
            List<T> list = mapper.selectByExample(example);
            if (list.isEmpty()) {
                throw exception;
            }
        } catch (BusinessException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 将实体page转成dtopage
     * @param page 要转化的实体page
     * @param function 转化逻辑
     * @param <T> 实体类型
     * @param <D> DTO类型
     * @return 转化后的dtopage
     */
    public static <T, D> Page<D> toDTOPage(Page<T> page, Function<T, D> function) {
        Page<D> res = new Page<>(page.getPageNum(), page.getPageSize());
        res.setTotal(page.getTotal());
        for (T t : page) {
            D d = function.apply(t);
            res.add(d);
        }
        return res;
    }

    private static Field getIdField(Class<?> clazz) {
        Field idField = null;
        Field[] allFields = BeanUtils.getAllFields(clazz);
        for (Field field1 : allFields) {
            if ("id".equals(field1.getName())) {
                idField = field1;
                idField.setAccessible(true);
            }
        }
        if (idField == null) {
            throw new RuntimeException("实体类id字段未找到");
        }
        return idField;
    }
}
