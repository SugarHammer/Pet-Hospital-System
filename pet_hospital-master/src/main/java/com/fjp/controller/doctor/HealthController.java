package com.fjp.controller.doctor;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.pojo.dto.doctor.HealthDTO;
import com.fjp.pojo.query.doctor.HealthQuery;
import com.fjp.service.doctor.HealthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/health")
@Api("日常健康管理")
public class HealthController {
    @Autowired
    private HealthService healthService;

    @GetMapping("")
    @ApiOperation("查询日常健康")
    @OperationLogAnnotation(operModul = "日常健康管理模块-查询日常健康",operType = "查询日常健康",operDesc = "查询日常健康")
    public List<HealthDTO> commonQuery(HealthQuery healthQuery) {
        return healthService.commonQuery(healthQuery);
    }

    @PostMapping("")
    @ApiOperation("添加日常健康")
    @OperationLogAnnotation(operModul = "日常健康管理模块-添加日常健康",operType = "添加日常健康",operDesc = "添加日常健康")
    public String addHealth(@RequestBody @Valid HealthDTO healthDTO) {
        return healthService.addHealth(healthDTO);
    }

    @PutMapping("")
    @ApiOperation("修改日常健康")
    @OperationLogAnnotation(operModul = "日常健康管理模块-修改日常健康",operType = "修改日常健康",operDesc = "修改日常健康")
    public String updateHealth(@RequestBody @Valid HealthDTO healthDTO) {
        return healthService.updateHealth(healthDTO);
    }

    @DeleteMapping("")
    @ApiOperation("删除日常健康")
    @OperationLogAnnotation(operModul = "日常健康管理模块-删除日常健康",operType = "删除日常健康",operDesc = "删除日常健康")
    public String deleteHealth(@RequestBody HealthDTO healthDTO) {
        return healthService.deleteHealth(healthDTO);
    }
}
