package com.fjp.pojo.dto.notice;

import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class AddNoticeDTO extends BaseDTO {

    @ApiModelProperty("描述")
    @NotNull
    private String description;

    @ApiModelProperty("标题")
    @NotNull
    private String title;
}
