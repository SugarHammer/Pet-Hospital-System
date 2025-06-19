package com.fjp.pojo.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案DTO
 */
@Data
@ToString
@Builder
public class PetDTO implements Serializable {
    @ApiModelProperty("客户名称")
    private String userName;

    @ApiModelProperty("宠物名称")
    private String name;

    @ApiModelProperty("种类")
    private String species;

    @ApiModelProperty("品种")
    private String varieties;

    @ApiModelProperty("身高")
    private Double height;

    @ApiModelProperty("体重")
    private Double weight;

    @ApiModelProperty("图片")
    private String picturelURL;

    @ApiModelProperty("是否住院 0不住院 1住院")
    private Integer inHospital;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    protected Long id;

    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    protected Date addTime;
}
