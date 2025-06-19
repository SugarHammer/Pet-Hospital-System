package com.fjp.pojo.dto.guide;

import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddGuideDTO extends BaseDTO {

    @ApiModelProperty("描述")
    @NotNull
    private String description;

    @ApiModelProperty("标题")
    @NotNull
    private String title;
}
