package com.fjp.pojo.query.basedata;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResourceQuery {
    @ApiModelProperty("用户id")
    private Long userId;
    @ApiModelProperty("角色id")
    private Long roleId;
}
