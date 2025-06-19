package com.fjp.pojo.entity.basedata;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "operation_log")
@Data
public class OperationLog {
    @Id
    private Long id;

    private String userCode;

    private String ip;

    private String type;

    private String description;

    private String model;

    private Date operationTime;

    private String result;
}
