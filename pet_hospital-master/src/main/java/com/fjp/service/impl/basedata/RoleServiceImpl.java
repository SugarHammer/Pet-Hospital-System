package com.fjp.service.impl.basedata;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.basedata.RoleHasResourceMapper;
import com.fjp.mapper.basedata.RoleMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.entity.basedata.Role;
import com.fjp.pojo.entity.basedata.RoleHasResource;
import com.fjp.service.basedata.ResourceService;
import com.fjp.service.basedata.RoleService;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleHasResourceMapper roleHasResourceMapper;

    @Autowired
    private ResourceService resourceService;

    @Override
    public List<RoleDTO> queryByUserId(Long userId) {
        List<Role> roles = roleMapper.queryByUserId(userId);
        return roles.stream()
                .map(this::roleToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleDTO> queryAll() {
        List<Role> roles = roleMapper.selectAll();
        return roles.stream()
                .map(this::roleToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateRole(RoleDTO roleDTO) {
        Role role = BeanUtils.copy(roleDTO, Role.class);
        // 验证数据合法性
        BusinessException exception = new BusinessException(ErrorCode.ROLE_ID_NOT_FOUND);
        GlobalUtils.checkExistId(role, roleMapper, exception);
        Map<String, BusinessException> map = new HashMap<>();
        map.put("name", new BusinessException(ErrorCode.ROLE_NAME_ALREADY_EXIST));
        GlobalUtils.checkColumnExist(role, map, roleMapper, true);
        // 修改角色
        roleMapper.updateByPrimaryKeySelective(role);
        // 修改角色所拥有的资源
        Example example = new Example(RoleHasResource.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleDTO.getId());
        roleHasResourceMapper.deleteByExample(example);
        List<RoleHasResource> list = new ArrayList<>();
        for (long resourceId : roleDTO.getResourceIds()) {
            list.add(new RoleHasResource(roleDTO.getId(), resourceId));
        }
        roleHasResourceMapper.insertList(list);
        return "修改成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addRole(RoleDTO roleDTO) {
        Role role = BeanUtils.copy(roleDTO, Role.class);

        Map<String, BusinessException> map = new HashMap<>();
        map.put("name", new BusinessException(ErrorCode.ROLE_NAME_ALREADY_EXIST));
        GlobalUtils.checkColumnExist(role, map, roleMapper, false);

        roleMapper.insert(role);
        return "添加成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteRole(RoleDTO roleDTO) {
        Role role = BeanUtils.copy(roleDTO, Role.class);
        BusinessException exception = new BusinessException(ErrorCode.ROLE_ID_NOT_FOUND);
        GlobalUtils.checkExistId(role, roleMapper, exception);
        roleMapper.deleteByPrimaryKey(role);
        return "删除成功";
    }

    private RoleDTO roleToDTO(Role role) {
        RoleDTO roleDTO = BeanUtils.copy(role, RoleDTO.class);
        roleDTO.setResourceDTOList(resourceService.queryByRoleId(role.getId()));
        return roleDTO;
    }
}
