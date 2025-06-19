package com.fjp.service.basedata;

import com.fjp.pojo.dto.basedata.UpdatePasswordDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.query.basedata.UserQuery;

import java.util.List;

public interface UserService {
    List<UserDTO> commonQuery(UserQuery userQuery);

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return 生成的token
     */
    String login(String username, String password);

    Boolean add(UserDTO userDTO);

    UserDTO queryById(Long id);

    String updateUser(UserDTO userDTO);

    String register(UserDTO userDTO);

    String updatePersonalInfo(UserDTO userDTO);

    String updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
