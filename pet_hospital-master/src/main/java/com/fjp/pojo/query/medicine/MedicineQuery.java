package com.fjp.pojo.query.medicine;

import com.fjp.pojo.query.PageQuery;
import lombok.Data;

@Data
public class MedicineQuery extends PageQuery {
    private String name;

    private String type;
}
