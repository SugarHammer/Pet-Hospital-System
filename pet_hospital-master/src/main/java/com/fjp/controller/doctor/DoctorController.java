package com.fjp.controller.doctor;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.pojo.dto.doctor.DoctorDTO;
import com.fjp.pojo.query.doctor.DoctorQuery;
import com.fjp.service.doctor.DoctorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doctor")
@Api("医生管理")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @GetMapping("")
    @ApiOperation("查询医生")
    @OperationLogAnnotation(operModul = "医生管理模块-查询医生",operType = "查询医生",operDesc = "查询医生")
    public List<DoctorDTO> doctorList(DoctorQuery doctorQuery) {
        return doctorService.commonQuery(doctorQuery);
    }

    @PostMapping("")
    @ApiOperation("添加医生")
    @OperationLogAnnotation(operModul = "医生管理模块-添加医生",operType = "添加医生",operDesc = "添加医生")
    public String addDoctor(@RequestBody @Valid DoctorDTO doctorDTO) {
        return doctorService.addDoctor(doctorDTO);
    }

    @PutMapping("")
    @ApiOperation("修改医生")
    @OperationLogAnnotation(operModul = "医生管理模块-修改医生",operType = "修改医生",operDesc = "修改医生")
    public String updateDoctor(@RequestBody @Valid DoctorDTO doctorDTO) {
        return doctorService.updateDoctor(doctorDTO);
    }

    @DeleteMapping("")
    @ApiOperation("删除医生")
    @OperationLogAnnotation(operModul = "医生管理模块-删除医生",operType = "删除医生",operDesc = "删除医生")
    public String deleteDoctor(@RequestBody DoctorDTO doctorDTO) {
        return doctorService.deleteDoctor(doctorDTO);
    }
}
