package com.fjp.pojo.entity.medicine;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 药品宠物记录表
 * 
 * @create-time 2021.10.5
 */
@Table(name = "pet_medicine")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PetMedicine extends BaseEntity {
    private Long petId;

    private Long medicineId;

    private Integer number;

    private String remark;
}
