package com.fjp.pojo.entity.pet;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 
 * @create_time 2021-9-24
 * @describe 宠物病例实体类
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "case_record")
@Data
@ToString(callSuper = true)
public class CaseRecord extends BaseEntity {
    /**
     * 宠物ID
     */
    private Long petId;

    /**
     * 医生id
     */
    private Long doctorId;

    /**
     * 描述
     */
    private String description;
}
