package com.fjp.controller.basedata;

import cn.hutool.jwt.JWT;
import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.basedata.UpdatePasswordDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.query.basedata.UserQuery;
import com.fjp.service.basedata.ResourceService;
import com.fjp.service.basedata.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
@Api("用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @GetMapping("")
    @ApiOperation("查询用户")
    @OperationLogAnnotation(operModul = "用户模块-查询用户",operType = "查询用户",operDesc = "查询用户")
    public List<UserDTO> userList(UserQuery userQuery) {
        return userService.commonQuery(userQuery);
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    @OperationLogAnnotation(operModul = "用户模块-用户登录",operType = "登录",operDesc = "用户登录")
    public String login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO.getUsername(), userDTO.getPassword());
    }

    @PostMapping("/register")
    @ApiOperation("注册")
    @OperationLogAnnotation(operModul = "用户模块-用户注册",operType = "注册",operDesc = "用户注册")
    public String register(@RequestBody @Valid UserDTO userDTO) {
        return userService.register(userDTO);
    }

    @PutMapping("")
    @ApiOperation("修改用户")
    @OperationLogAnnotation(operModul = "用户模块-修改用户",operType = "修改用户",operDesc = "修改用户")
    public String updateUser(@RequestBody @Valid UserDTO userDTO) {
        return userService.updateUser(userDTO);
    }

    @PutMapping("/updatePersonalInfo")
    @ApiOperation("修改个人信息")
    @OperationLogAnnotation(operModul = "用户模块-修改个人信息",operType = "修改个人信息",operDesc = "修改个人信息")
    public String updatePersonalInfo(@RequestBody @Valid UserDTO userDTO) {
        return userService.updatePersonalInfo(userDTO);
    }

    @PutMapping("/updatePassword")
    @ApiOperation("修改密码")
    @OperationLogAnnotation(operModul = "用户模块-修改密码",operType = "修改密码",operDesc = "修改密码")
    public String updatePassword(@RequestBody @Valid UpdatePasswordDTO updatePasswordDTO) {
        return userService.updatePassword(updatePasswordDTO);
    }

    @PostMapping("/logout")
    @ApiOperation("退出")
    @OperationLogAnnotation(operModul = "用户模块-退出",operType = "退出",operDesc = "退出")
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        JWT.of(token).setExpiresAt(new Date());
        return "退出成功";
    }

    @GetMapping("/info")
    @ApiOperation("根据token查询用户")
    //@OperationLogAnnotation(operModul = "用户模块-根据token查询用户",operType = "根据token查询用户",operDesc = "根据token查询用户")
    public UserDTO queryByToken(String token) {
        Long id = Long.parseLong(JWT.of(token).getPayload("id").toString());
        UserDTO userDTO = userService.queryById(id);
        if (userDTO == null) {
            throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
        }
        userDTO.setResourceDTOList(resourceService.queryByUserId(userDTO.getId()));
        return userDTO;
    }
}
