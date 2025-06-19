package com.fjp.controller.basedata;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.pojo.dto.basedata.ResourceDTO;
import com.fjp.pojo.query.basedata.ResourceQuery;
import com.fjp.service.basedata.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/resource")
@Api("资源管理")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @GetMapping("")
    @ApiOperation("查询资源")
    @OperationLogAnnotation(operModul = "资源管理模块-查询资源",operType = "查询资源",operDesc = "查询资源")
    public List<ResourceDTO> commonQuery(@Valid ResourceQuery resourceQuery) {
        return resourceService.commonQuery(resourceQuery);
    }

}
