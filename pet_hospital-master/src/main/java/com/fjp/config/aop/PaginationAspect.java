package com.fjp.config.aop;

import com.fjp.pojo.query.PageQuery;
import com.fjp.util.BaseContextHolder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PaginationAspect {
    @Around("@annotation(com.fjp.config.annotation.Pagination) && " +
            "args(pageQuery)")
    public Object setPage(ProceedingJoinPoint point, PageQuery pageQuery) throws Throwable {
        PageHelper.startPage(pageQuery.getPage(), pageQuery.getSize());
        Object proceed = point.proceed();
        if (proceed instanceof Page) {
            BaseContextHolder.set("page", proceed);
        }
        return proceed;
    }
}
