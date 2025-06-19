package com.fjp.pojo.entity.medicine;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;

/**
 * 药品
 * 
 * @create-time 2021.10.5
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "medicine")
@Data
@ToString(callSuper = true)
public class Medicine extends BaseEntity {
    private String name;

    private Integer number;

    private String treatment;

    private String company;

    private Double price;

    private String remark;

    private String type;
}
