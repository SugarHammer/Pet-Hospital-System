package com.fjp.mapper.basedata;

import com.fjp.pojo.entity.basedata.Role;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface RoleMapper extends Mapper<Role> {
    List<Role> queryByUserId(Long userId);
}
