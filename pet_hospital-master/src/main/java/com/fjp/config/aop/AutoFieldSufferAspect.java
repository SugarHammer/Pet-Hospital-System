package com.fjp.config.aop;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.enums.AutoFieldType;
import com.fjp.pojo.dto.BaseDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class AutoFieldSufferAspect {

    @Around("@annotation(com.fjp.config.annotation.AutoFieldSuffer) && " +
            "args(baseDTO)")
    public Object autoFieldSuffer(ProceedingJoinPoint point, BaseDTO baseDTO) throws Throwable {
        Class<?> aClass = point.getTarget().getClass();
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method targetMethod = aClass.getDeclaredMethod(ms.getName(), ms.getParameterTypes());
        AutoFieldSuffer autoFieldSuffer = targetMethod.getAnnotation(AutoFieldSuffer.class);
        if (autoFieldSuffer.value() == AutoFieldType.ADD) {
            Snowflake snowflake = IdUtil.getSnowflake(1, 1);
            Long id = snowflake.nextId();
            baseDTO.setId(id);
            baseDTO.setAddTime(new Date());
            baseDTO.setUpdateTime(new Date());
        } else if (autoFieldSuffer.value() == AutoFieldType.UPDATE) {
            baseDTO.setUpdateTime(new Date());
        }
        return point.proceed();
    }
}
