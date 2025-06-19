package com.fjp.mapper.basedata;

import com.fjp.pojo.entity.basedata.RoleHasResource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface RoleHasResourceMapper extends Mapper<RoleHasResource>, InsertListMapper<RoleHasResource> {
}
