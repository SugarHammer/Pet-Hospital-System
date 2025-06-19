package com.fjp.mapper.basedata;

import com.fjp.pojo.entity.basedata.Resource;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface ResourceMapper extends Mapper<Resource> {
    List<Resource> queryByUserId(Long userId);

    List<Resource> queryByRoleId(Long roleId);
}
