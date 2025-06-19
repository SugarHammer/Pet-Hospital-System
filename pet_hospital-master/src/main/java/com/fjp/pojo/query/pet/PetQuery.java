package com.fjp.pojo.query.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.query.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案Query
 */
@Data
public class PetQuery extends PageQuery {
    @ApiModelProperty("宠物ID")
    private Long id;

    @ApiModelProperty("宠物名称")
    private String name;

    @ApiModelProperty("宠物品种")
    private String varieties;

    @ApiModelProperty("宠物种类")
    private String species;

    @ApiModelProperty("查询人ID,不需要传入")
    private Long findID;

    @ApiModelProperty("添加时间-开始")
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date addTime;

    @ApiModelProperty("是否住院 0不住院 1住院")
    private Integer inHospital;

    @ApiModelProperty("用户名称")
    private String userName;
}
