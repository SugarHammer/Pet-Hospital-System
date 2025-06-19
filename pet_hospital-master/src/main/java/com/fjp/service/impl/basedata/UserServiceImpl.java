package com.fjp.service.impl.basedata;

import cn.hutool.crypto.digest.MD5;
import cn.hutool.jwt.JWT;
import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.basedata.UserHasRoleMapper;
import com.fjp.mapper.basedata.UserMapper;
import com.fjp.pojo.dto.basedata.ResourceDTO;
import com.fjp.pojo.dto.basedata.UpdatePasswordDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.basedata.UserHasRole;
import com.fjp.pojo.query.basedata.UserQuery;
import com.fjp.service.basedata.ResourceService;
import com.fjp.service.basedata.RoleService;
import com.fjp.service.basedata.UserService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import com.fjp.pojo.dto.basedata.RoleDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserHasRoleMapper userHasRoleMapper;

    @Value("${jwt-key}")
    private String jwtKey;

    @Override
    @Pagination
    public List<UserDTO> commonQuery(UserQuery userQuery) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        if (userQuery.getUsername() != null) {
            criteria.orLike("username", "%" + userQuery.getUsername().trim() + "%");
        }
        if (userQuery.getName() != null) {
            criteria.orLike("name", "%" + userQuery.getName().trim() + "%");
        }
        if (userQuery.getIdCard() != null) {
            criteria.orLike("idCard", "%" + userQuery.getIdCard().trim() + "%");
        }
        if (userQuery.getPhone() != null) {
            criteria.orLike("phone", "%" + userQuery.getPhone().trim() + "%");
        }
        List<User> users = userMapper.selectByExample(example);
        if (!(users instanceof Page)) {
            throw new BusinessException(ErrorCode.PAGE_SEARCH_EXCEPTION);
        }
        Page<User> page = (Page<User>) users;
        return GlobalUtils.toDTOPage(page, this::userToDTO);
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    public Boolean add(UserDTO userDTO) {
        System.out.println(userDTO);
        return true;
    }

    @Override
    public String login(String username, String password) {
        password = MD5.create().digestHex(password);
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        User user = userMapper.selectOneByExample(example);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_OR_PASSWORD_INVALID);
        }
        return JWT.create()
                .setPayload("username", user.getUsername())
                .setPayload("name", user.getName())
                .setPayload("id", user.getId())
                .setKey(jwtKey.getBytes()).sign();
    }

    @Override
    public UserDTO queryById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
        }
        return userToDTO(user);
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    @Transactional(rollbackFor = Exception.class)
    public String updateUser(UserDTO userDTO) {
        User user = BeanUtils.copy(userDTO, User.class);
        user.setPassword(null);
        // 验证数据合法性
        BusinessException exception = new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
        GlobalUtils.checkExistId(user, userMapper, exception);
        checkUserValid(user, true);
        // 修改用户
        userMapper.updateByPrimaryKeySelective(user);
        //  为用户分配角色
        Example example = new Example(UserHasRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userDTO.getId());
        userHasRoleMapper.deleteByExample(example);
        List<RoleDTO> roleList = userDTO.getRoleDTOList();
        for (RoleDTO role : roleList) {
            userHasRoleMapper.insert(new UserHasRole(userDTO.getId(), role.getId()));
        }
        return "修改成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String register(UserDTO userDTO) {
        User user = BeanUtils.copy(userDTO, User.class);
        user.setStatus(true);
        String password = userDTO.getPassword();
        user.setPassword(MD5.create().digestHex(password));
        checkUserValid(user, false);
        userMapper.insert(user);
        // 分配客户角色
        userHasRoleMapper.insert(new UserHasRole(userDTO.getId(), 1440120216490217472L));
        return "注册成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updatePersonalInfo(UserDTO userDTO) {
        UserDTO currentUser = (UserDTO) BaseContextHolder.get("user");
        userDTO.setId(currentUser.getId());
        userDTO.setStatus(currentUser.getStatus());

        User user = BeanUtils.copy(userDTO, User.class);
        user.setPassword(null);

        BusinessException exception = new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
        GlobalUtils.checkExistId(user, userMapper, exception);
        checkUserValid(user, true);
        userMapper.updateByPrimaryKeySelective(user);

        return "修改成功";
    }

    @Override
    public String updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        User user = new User();
        String oldPassword = MD5.create().digestHex(updatePasswordDTO.getOldPassword());
        UserDTO currentUser = (UserDTO) BaseContextHolder.get("user");
        User temp = userMapper.selectByPrimaryKey(currentUser.getId());
        if (!oldPassword.equals(temp.getPassword())) {
            throw new BusinessException(ErrorCode.OLD_PASSWORD_NOT_FOUND);
        }

        user.setId(currentUser.getId());
        String newPassword = MD5.create().digestHex(updatePasswordDTO.getNewPassword());
        user.setPassword(newPassword);

        userMapper.updateByPrimaryKeySelective(user);
        return "修改成功";
    }

    private UserDTO userToDTO(User user) {
        UserDTO userDTO = BeanUtils.copy(user, UserDTO.class);
        userDTO.setPassword(null);
        List<ResourceDTO> resourceDTOList = resourceService.queryByUserId(user.getId());
        userDTO.setResourceDTOList(resourceDTOList);
        List<RoleDTO> roleDTOS = roleService.queryByUserId(user.getId());
        userDTO.setRoleDTOList(roleDTOS);
        return userDTO;
    }

    private void checkUserValid(User user, boolean ignore) {
        Map<String, BusinessException> map = new HashMap<>();
        map.put("username", new BusinessException(ErrorCode.USERNAME_ALREADY_EXIST));
        map.put("phone", new BusinessException(ErrorCode.PHONE_ALREADY_USED));
        map.put("idCard", new BusinessException(ErrorCode.IDCARD_ALREADY_USED));
        GlobalUtils.checkColumnExist(user, map, userMapper, ignore);
    }
}
