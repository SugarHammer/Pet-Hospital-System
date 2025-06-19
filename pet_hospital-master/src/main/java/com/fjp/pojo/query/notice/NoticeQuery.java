package com.fjp.pojo.query.notice;

import com.fjp.pojo.query.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
@Data
public class NoticeQuery extends PageQuery {
    @ApiModelProperty("通知id")
    private Long id;
    @ApiModelProperty("通知人Id")
    private Long userId;
    @ApiModelProperty("通知描述")
    private String description;
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("增加时间")
    private Date addTime;
}
