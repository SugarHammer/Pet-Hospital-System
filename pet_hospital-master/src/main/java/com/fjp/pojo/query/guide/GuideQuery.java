package com.fjp.pojo.query.guide;

import com.fjp.pojo.query.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class GuideQuery extends PageQuery {
    @ApiModelProperty("指南id")
    private Long id;
    @ApiModelProperty("指南发布者Id")
    private Long userId;
    @ApiModelProperty("指南描述")
    private String description;
    @ApiModelProperty("指南标题")
    private String title;
    @ApiModelProperty("增加时间")
    private Date addTime;

}
