package com.fjp.pojo.entity.basedata;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@Table(name = "role_has_resource")
public class RoleHasResource {
    private Long roleId;
    private Long resourceId;
}
