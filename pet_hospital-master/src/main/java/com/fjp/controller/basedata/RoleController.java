package com.fjp.controller.basedata;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.service.basedata.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
@Api("角色管理")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("")
    @OperationLogAnnotation(operModul = "角色管理模块-查询角色",operType = "查询全部角色",operDesc = "查询全部角色")
    public List<RoleDTO> getAll() {
        return roleService.queryAll();
    }

    @PutMapping("")
    @OperationLogAnnotation(operModul = "角色管理模块-更新角色",operType = "更新角色",operDesc = "更新角色")
    public String updateRole(@RequestBody @Valid RoleDTO roleDTO) {
        return roleService.updateRole(roleDTO);
    }

    @PostMapping("")
    @OperationLogAnnotation(operModul = "角色管理模块-添加角色",operType = "添加角色",operDesc = "添加角色")
    public String addRole(@RequestBody @Valid RoleDTO roleDTO) {
        return roleService.addRole(roleDTO);
    }

    @DeleteMapping("")
    @OperationLogAnnotation(operModul = "角色管理模块-删除角色",operType = "删除角色",operDesc = "删除角色")
    public String deleteRole(@RequestBody RoleDTO roleDTO) {
        return roleService.deleteRole(roleDTO);
    }
}
