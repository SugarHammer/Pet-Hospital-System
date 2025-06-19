package com.fjp.config.handler;

import cn.hutool.jwt.JWT;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.basedata.ResourceDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.service.basedata.UserService;
import com.fjp.util.BaseContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
@CrossOrigin
public class UserContextHandler implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Value("${whiteList}")
    private List<String> whiteList;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            Long id = Long.parseLong(JWT.of(token).getPayload("id").toString());
            UserDTO userDTO = userService.queryById(id);
            BaseContextHolder.set("user", userDTO);
            if (whiteList.contains(request.getRequestURI())) {
                return true;
            }
            List<ResourceDTO> resourceDTOList = userDTO.getResourceDTOList();
            for (ResourceDTO resourceDTO : resourceDTOList) {
                if (resourceDTO.getType() == 0) {
                    String resourceUrl = resourceDTO.getUrl();
                    String url = resourceUrl.split(",")[0];
                    String method = resourceUrl.split(",")[1];
                    if (url.equals(request.getRequestURI()) && method.equalsIgnoreCase(request.getMethod())) {
                        return true;
                    }
                }
            }
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        }
        else if (!whiteList.contains(request.getRequestURI())) {
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        }
        return true;
    }
}
