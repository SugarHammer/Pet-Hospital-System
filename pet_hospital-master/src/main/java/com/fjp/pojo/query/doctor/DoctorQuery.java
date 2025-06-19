package com.fjp.pojo.query.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import com.fjp.pojo.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class DoctorQuery extends PageQuery {
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
}
