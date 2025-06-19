package com.fjp.pojo.dto.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 
 * @create_time 2021-9-25
 * @describe 增加宠物病例DTO
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCaseRecordDTO extends BaseDTO {
    @ApiModelProperty("医生id")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long doctorId;

    @ApiModelProperty("描述")
    @NotNull
    private String description;

    @ApiModelProperty("宠物ID")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long petId;
}
