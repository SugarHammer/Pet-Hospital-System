package com.fjp.pojo.entity.doctor;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class Appointment extends BaseEntity {
    private Long doctorId;
    private Long userId;
    private Long petId;
    private Date appointDate;
    private String status;
    private Double money;
}
