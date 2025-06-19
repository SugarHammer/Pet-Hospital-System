package com.fjp.pojo.entity.basedata;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Table;

@Data
@AllArgsConstructor
@Table(name = "user_has_role")
public class UserHasRole {
    private Long userId;
    private Long roleId;
}
