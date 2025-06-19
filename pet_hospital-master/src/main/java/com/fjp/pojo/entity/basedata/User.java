package com.fjp.pojo.entity.basedata;

import com.fjp.pojo.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class User extends BaseEntity {

    private String username;

    private String password;

    private String name;

    private String sex;

    private String idCard;

    private String phone;

    private String address;

    private Boolean status;

}
