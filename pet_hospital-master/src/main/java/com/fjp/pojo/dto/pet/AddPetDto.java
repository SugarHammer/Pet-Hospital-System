package com.fjp.pojo.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @create_time 2021-9-22
 * @describe 客户增加宠物档案DTO
 */
@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("客户添加宠物档案dto")
public class AddPetDto extends BaseDTO implements Serializable {
    @ApiModelProperty("宠物名称")
    @NotNull
    private String name;

    @ApiModelProperty("种类")
    @NotNull
    private String species;

    @ApiModelProperty("品种")
    @NotNull
    private String varieties;

    @ApiModelProperty("身高")
    @NotNull
    private Double height;

    @ApiModelProperty("体重")
    @NotNull
    private Double weight;

    @ApiModelProperty("图片")
    private String pictureUrl;

    @ApiModelProperty("是否住院 0不住院 1住院")
    @NotNull
    @Max(1)
    @Min(0)
    private Integer inHospital;

    @ApiModelProperty("出生年月")
    @NotNull
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birth;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "客户编号",notes = "客户不需要传递")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;

}
