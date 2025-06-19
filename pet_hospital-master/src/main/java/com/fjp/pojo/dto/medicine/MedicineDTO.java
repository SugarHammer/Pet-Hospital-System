package com.fjp.pojo.dto.medicine;

import com.fjp.pojo.dto.BaseDTO;
import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 药品
 * 
 * @create-time 2021.10.5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MedicineDTO extends BaseDTO {
    @NotNull
    private String name;

    @NotNull
    private Integer number;

    @NotNull
    private String treatment;

    @NotNull
    private String company;

    @NotNull
    private Double price;

    @NotNull
    private String type;

    private String remark;
}
