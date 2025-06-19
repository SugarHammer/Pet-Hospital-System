package com.fjp.service.basedata;

import com.fjp.pojo.dto.basedata.RoleDTO;

import java.util.List;

public interface RoleService {
    List<RoleDTO> queryByUserId(Long userId);

    List<RoleDTO> queryAll();

    String updateRole(RoleDTO roleDTO);

    String addRole(RoleDTO roleDTO);

    String deleteRole(RoleDTO roleDTO);
}
