package com.fjp.pojo.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.pet.Pet;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class HealthDTO extends BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long petId;
    @NotEmpty(message = "描述不能为空")
    private String description;
    @NotNull(message = "身高不能为空")
    private Double height;
    @NotNull(message = "体重不能为空")
    private Double weight;
    @NotEmpty(message = "健康状态不能为空")
    private String healthStatus;

    private Pet pet;
    private User addUser;
    private User owner;
}
