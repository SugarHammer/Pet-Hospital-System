package com.fjp.pojo.query.doctor;

import com.fjp.pojo.query.PageQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HealthQuery extends PageQuery {
    private String petName;
    private String userName;
}
