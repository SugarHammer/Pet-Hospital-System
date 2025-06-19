package com.fjp.pojo.query;

import lombok.Data;

import javax.validation.constraints.Min;

/**
 * 分页查询
 */
@Data
public abstract class PageQuery {
    @Min(1)
    private int page = 1;
    private int size = 5;
}
