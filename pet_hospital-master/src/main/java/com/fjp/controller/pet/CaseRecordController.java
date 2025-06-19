package com.fjp.controller.pet;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.pet.AddCaseRecordDTO;
import com.fjp.pojo.dto.pet.CaseRecordDTO;
import com.fjp.service.pet.CaseRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物病例 controller
 */
@RestController
@RequestMapping("/caseRecord")
@Api("宠物病例")
public class CaseRecordController {
    @Autowired
    private CaseRecordService caseRecordService;

    @GetMapping("/getById")
    @ApiOperation("通过id获取宠物病例")
    @OperationLogAnnotation(operModul = "宠物病例模块-通过id获取宠物病例",operType = "通过id获取宠物病例",operDesc = "通过id获取宠物病例")
    public List<CaseRecordDTO> getById(Long id){
        return caseRecordService.findAll(id);
    }

    @PostMapping("/add")
    @ApiOperation("添加宠物病例")
    @OperationLogAnnotation(operModul = "宠物病例模块-添加宠物病例",operType = "添加宠物病例",operDesc = "添加宠物病例")
    public String add(@RequestBody AddCaseRecordDTO addCaseRecordDTO) {
        return caseRecordService.addCaseRecord(addCaseRecordDTO);
    }

    @PostMapping("/delete")
    @ApiOperation("删除宠物病例")
    @OperationLogAnnotation(operModul = "宠物病例模块-删除宠物病例",operType = "删除宠物病例",operDesc = "删除宠物病例")
    public String delete(Long id) {
        return caseRecordService.deleteCaseRecord(id);
    }

    @GetMapping("/getByCaseId")
    @ApiOperation("查看详细病例")
    @OperationLogAnnotation(operModul = "宠物病例模块-查看详细病例",operType = "查看详细病例",operDesc = "查看详细病例")
    public AddCaseRecordDTO getByCaseId(Long id) {
        if (id == null)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        return caseRecordService.findCaseRecordById(id);
    }

    @PostMapping("/update")
    @ApiOperation("更新病例")
    @OperationLogAnnotation(operModul = "宠物病例模块-更新病例",operType = "更新病例",operDesc = "更新病例")
    public String update(@RequestBody @Valid AddCaseRecordDTO addCaseRecordDTO) {
        return caseRecordService.updateCaseRecord(addCaseRecordDTO);
    }
}
