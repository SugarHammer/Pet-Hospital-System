package com.fjp.pojo.entity.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "doctor")
@Data
@EqualsAndHashCode(callSuper = true)
public class Doctor extends BaseEntity {
    private String name;
    private String sex;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birth;
    private String title;
    private String department;
    private String phone;
    private String doctorPracticeCertificateNo;
    private String education;
    private String idCard;
    private String address;
}
