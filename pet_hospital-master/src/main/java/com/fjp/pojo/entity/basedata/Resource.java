package com.fjp.pojo.entity.basedata;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@Table(name = "resource")
@Data
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseEntity {
    private String name;
    private String url;
    private String src;
    private String icon;
    private Long parentId;
    private Integer type;
}
