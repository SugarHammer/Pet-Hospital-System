package com.fjp.config.aop;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.mapper.basedata.OperationLogDao;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.entity.basedata.OperationLog;
import com.fjp.util.BaseContextHolder;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author lyz
 * @title: OperationAspect
 * @projectName springcloud
 * @date 2020/9/23
 * @description: 操作日志切面处理类
 */
@Aspect
@Component
public class OperationLogAspect {
    @Autowired
    OperationLogDao logDao;
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 设置操作日志切入点   在注解的位置切入代码
     */
    @Pointcut("@annotation(com.fjp.config.annotation.OperationLogAnnotation)")
    public void operLogPoinCut() {
    }


    @AfterReturning(returning  /**
     * 记录操作日志
     * @param joinPoint 方法的执行点
     * @param result  方法返回值
     * @throws Throwable
     */ = "result", value = "operLogPoinCut()")
    public void saveOperLog(JoinPoint joinPoint, Object result) throws Throwable {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            //将返回值转换成map集合
            OperationLog operationLog = new OperationLog();
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取操作
            OperationLogAnnotation annotation = method.getAnnotation(OperationLogAnnotation.class);
            if (annotation != null) {
                operationLog.setModel(annotation.operModul());
                operationLog.setType(annotation.operType());
                operationLog.setDescription(annotation.operDesc());
            }
            //操作时间
            operationLog.setOperationTime(Timestamp.valueOf(sdf.format(new Date())));
            //操作用户
            UserDTO user = (UserDTO) BaseContextHolder.get("user");
            if (user != null)
                operationLog.setUserCode(user.getUsername());
            //操作IP
            //operationLog.setIp(IPUtil.getIpAdrress(request));
            //返回值信息
            operationLog.setResult(result.toString());
            //保存日志
            logDao.insert(operationLog);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
