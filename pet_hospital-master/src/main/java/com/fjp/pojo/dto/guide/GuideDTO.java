package com.fjp.pojo.dto.guide;

import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class GuideDTO extends BaseDTO {
    @ApiModelProperty("通知人姓名")
    private String username;
    @ApiModelProperty("通知人Id")
    private Long userId;
    @ApiModelProperty("通知描述")
    private String description;
    @ApiModelProperty("标题")
    private String title;
}
