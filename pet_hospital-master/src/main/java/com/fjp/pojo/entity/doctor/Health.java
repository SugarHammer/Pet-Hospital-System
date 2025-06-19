package com.fjp.pojo.entity.doctor;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@Table(name = "health")
@Data
@EqualsAndHashCode(callSuper = true)
public class Health extends BaseEntity {
    private Long userId;
    private Long petId;
    private String description;
    private Double height;
    private Double weight;
    private String healthStatus;
}
