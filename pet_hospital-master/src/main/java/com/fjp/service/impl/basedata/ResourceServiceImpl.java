package com.fjp.service.impl.basedata;

import com.fjp.mapper.basedata.ResourceMapper;
import com.fjp.pojo.dto.basedata.ResourceDTO;
import com.fjp.pojo.entity.basedata.Resource;
import com.fjp.pojo.query.basedata.ResourceQuery;
import com.fjp.service.basedata.ResourceService;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    private ResourceMapper resourceMapper;

    @Override
    public List<ResourceDTO> commonQuery(ResourceQuery resourceQuery) {
        if (resourceQuery.getUserId() != null) {
            return queryByUserId(resourceQuery.getUserId());
        } else if (resourceQuery.getRoleId() != null) {
            return queryByRoleId(resourceQuery.getRoleId());
        }
        return queryAll();
    }

    @Override
    public List<ResourceDTO> queryByRoleId(Long roleId) {
        List<Resource> resources = resourceMapper.queryByRoleId(roleId);
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        treeResource(resources, resourceDTOList);
        return resourceDTOList.stream().filter(resourceDTO -> resourceDTO.getParentId() == null).collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> queryByUserId(Long userId) {
        List<Resource> resources = resourceMapper.queryByUserId(userId);
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        treeResource(resources, resourceDTOList);
        return resourceDTOList.stream().filter(resourceDTO -> resourceDTO.getParentId() == null).collect(Collectors.toList());
    }

    @Override
    public List<ResourceDTO> queryAll() {
        List<Resource> resources = resourceMapper.selectAll();
        List<ResourceDTO> resourceDTOList = new ArrayList<>();
        treeResource(resources, resourceDTOList);
        return resourceDTOList.stream().filter(resourceDTO -> resourceDTO.getParentId() == null).collect(Collectors.toList());
    }

    private void treeResource(List<Resource> resources, List<ResourceDTO> resourceDTOList) {
        Map<Long, ResourceDTO> map = new HashMap<>();
        for (Resource resource : resources) {
            ResourceDTO resourceDTO = BeanUtils.copy(resource, ResourceDTO.class);
            map.put(resource.getId(), resourceDTO);
            resourceDTOList.add(resourceDTO);
        }
        for (ResourceDTO resourceDTO : resourceDTOList) {
            if (resourceDTO.getParentId() != null) {
                ResourceDTO parent = map.get(resourceDTO.getParentId());
                if (parent.getChildren() == null) {
                    parent.setChildren(new ArrayList<>());
                }
                parent.getChildren().add(resourceDTO);
            }
        }
    }
}
