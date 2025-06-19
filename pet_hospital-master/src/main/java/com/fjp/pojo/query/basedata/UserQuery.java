package com.fjp.pojo.query.basedata;

import com.fjp.pojo.query.PageQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery {
    private String name;
    private String username;
    private String idCard;
    private String phone;
}
