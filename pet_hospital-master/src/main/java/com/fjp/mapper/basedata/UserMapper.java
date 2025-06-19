package com.fjp.mapper.basedata;

import com.fjp.pojo.entity.basedata.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface UserMapper extends Mapper<User> {
}
