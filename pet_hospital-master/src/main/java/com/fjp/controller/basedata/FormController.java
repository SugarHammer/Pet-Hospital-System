package com.fjp.controller.basedata;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.service.basedata.FormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/form")
@Api("统计报表")
public class FormController {
    @Autowired
    private FormService formService;

    @GetMapping("/petPercentage")
    @ApiOperation("统计宠物物种占比")
    @OperationLogAnnotation(operModul = "统计报表模块-统计宠物物种占比",operType = "统计宠物物种占比",operDesc = "统计宠物物种占比")
    public List<Map<String, Object>> petPercentage() {
        return formService.petPercentage();
    }

    @GetMapping("/appointmentCount")
    @ApiOperation("近七天的预约数量")
    @OperationLogAnnotation(operModul = "统计报表模块-近七天的预约数量",operType = "近七天的预约数量",operDesc = "近七天的预约数量")
    public List<Map<String, Object>> appointmentCount() {
        return formService.appointmentCount();
    }

    @GetMapping("/healthCount")
    @ApiOperation("前七天的健康状况统计")
    @OperationLogAnnotation(operModul = "统计报表模块-前七天的健康状况统计",operType = "前七天的健康状况统计",operDesc = "前七天的健康状况统计")
    public List<Map<String, Object>> healthCount() {
        return formService.healthCount();
    }
}
