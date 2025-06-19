package com.fjp.controller.guide;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.guide.GuideDTO;
import com.fjp.pojo.dto.guide.AddGuideDTO;
import com.fjp.pojo.entity.guide.Guide;
import com.fjp.pojo.query.guide.GuideQuery;
import com.fjp.service.guide.GuideService;
import com.fjp.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/guide")
@Api("指南管理")
public class GuideController {
    @Autowired
    private GuideService guideService;

    @GetMapping("/selectByid")
    @ApiOperation("查看详情")
    @OperationLogAnnotation(operModul = "指南管理模块-查看详情",operType = "查看详情",operDesc = "查看详情")
    public GuideDTO selectById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.GUIDE_ID_NOT_FOUND);
        }
        return guideService.selectById(id);
    }

    @GetMapping("/selectByGuideQuery")
    @ApiOperation("查询全部")
    @OperationLogAnnotation(operModul = "指南管理模块-查询全部",operType = "查询全部",operDesc = "查询全部")
    public List<GuideDTO> selectByGuideQuery(GuideQuery guideQuery) {
        return guideService.selectByGuideQuery(guideQuery);
    }

    @PostMapping("/addGuide")
    @ApiOperation("添加指南")
    @OperationLogAnnotation(operModul = "指南管理模块-添加指南",operType = "添加指南",operDesc = "添加指南")
    public String addGuide(@RequestBody @Valid AddGuideDTO addGuideDTO) {
        if (addGuideDTO.getId() != null)
            addGuideDTO.setId(null);
        return guideService.addGuide(addGuideDTO);
    }

    @DeleteMapping("/deleteGuide")
    @ApiOperation("删除指南")
    @OperationLogAnnotation(operModul = "指南管理模块-删除指南",operType = "删除指南",operDesc = "删除指南")
    public String deleteGuide(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.GUIDE_ID_NOT_FOUND);
        }
        return guideService.deleteGuide(id);
    }

    @PostMapping("/updateNotice")
    @ApiOperation("修改指南")
    @OperationLogAnnotation(operModul = "指南管理模块-修改指南",operType = "修改指南",operDesc = "修改指南")
    public String updateGuide(@RequestBody @Valid Guide guide) {
        if (guide == null) {
            throw new BusinessException(ErrorCode.GUIDE_ID_NOT_FOUND);
        }
        return guideService.updateGuide(guide);
    }
}
