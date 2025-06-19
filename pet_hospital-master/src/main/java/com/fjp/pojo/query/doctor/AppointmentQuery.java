package com.fjp.pojo.query.doctor;

import com.fjp.pojo.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppointmentQuery extends PageQuery {
    private String doctorName;
    private String userName;
    private String petName;
    private Date appointDate;
    private String status;
    private Double money;
}
