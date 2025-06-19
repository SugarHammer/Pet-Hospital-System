package com.fjp.service.basedata;

import com.fjp.pojo.dto.basedata.ResourceDTO;
import com.fjp.pojo.query.basedata.ResourceQuery;

import java.util.List;

public interface ResourceService {
    List<ResourceDTO> commonQuery(ResourceQuery resourceQuery);

    List<ResourceDTO> queryByRoleId(Long roleId);

    List<ResourceDTO> queryByUserId(Long userId);

    List<ResourceDTO> queryAll();
}
