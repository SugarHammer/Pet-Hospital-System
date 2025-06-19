package com.fjp.controller.doctor;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.pojo.dto.doctor.AppointmentDTO;
import com.fjp.pojo.query.doctor.AppointmentQuery;
import com.fjp.service.doctor.AppointmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
@Api("预约管理")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("")
    @ApiOperation("查询预约")
    @OperationLogAnnotation(operModul = "预约管理模块-删除角色",operType = "查询预约",operDesc = "查询预约")
    public List<AppointmentDTO> addAppointment(AppointmentQuery appointmentQuery) {
        return appointmentService.commonQuery(appointmentQuery);
    }

    @PostMapping("")
    @ApiOperation("添加预约")
    @OperationLogAnnotation(operModul = "预约管理模块-添加预约",operType = "添加预约",operDesc = "添加预约")
    public String addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.addAppointment(appointmentDTO);
    }

    @PutMapping("")
    @ApiOperation("修改预约")
    @OperationLogAnnotation(operModul = "预约管理模块-修改预约",operType = "修改预约",operDesc = "修改预约")
    public String updateAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return appointmentService.updateAppointment(appointmentDTO);
    }
}
