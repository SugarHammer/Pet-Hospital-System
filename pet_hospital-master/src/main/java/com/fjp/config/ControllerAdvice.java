package com.fjp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fjp.exceptions.BusinessException;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.CommonResponse;
import com.github.pagehelper.Page;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@Component
@RestControllerAdvice(basePackages = "com.fjp.controller")
public class ControllerAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        try {
            Object page = BaseContextHolder.get("page");
            if (page != null) {
                Page page1 = (Page) page;
                BaseContextHolder.set("page", null);
                return CommonResponse.of("操作成功", 200, o, page1.getTotal(), page1.getPageNum(), page1.getPageSize());
            }
            if (o instanceof String) {
                ObjectMapper om = new ObjectMapper();
                return om.writeValueAsString(CommonResponse.of("操作成功", 200, o));
            }
            return CommonResponse.of("操作成功", 200, o);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.of(e.getMessage(), 500, null);
        }
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public CommonResponse businessException(BusinessException e) {
        e.printStackTrace();
        return CommonResponse.of(e.getCode().getMsg(), e.getCode().getCode(), null);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        return CommonResponse.of(allErrors.get(0).getDefaultMessage(), 500, null);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public CommonResponse handleException(Exception e) {
        e.printStackTrace();
        return CommonResponse.of("系统出错了", 500, null);
    }
}
