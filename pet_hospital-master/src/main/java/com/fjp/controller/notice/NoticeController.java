package com.fjp.controller.notice;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.notice.AddNoticeDTO;
import com.fjp.pojo.dto.notice.NoticeDTO;
import com.fjp.pojo.entity.guide.Guide;
import com.fjp.pojo.entity.notice.Notice;
import com.fjp.pojo.query.notice.NoticeQuery;
import com.fjp.service.notice.NoticeService;
import com.fjp.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/notice")
@Api("通知管理")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/selectById")
    @ApiOperation("查看详情")
    @OperationLogAnnotation(operModul = "通知管理模块-查看详情",operType = "查看详情",operDesc = "查看详情")
    public NoticeDTO selectById(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        return noticeService.selectById(id);
    }

    @PostMapping("/selectByQuery")
    @ApiOperation("查询全部")
    @OperationLogAnnotation(operModul = "通知管理模块-查询全部",operType = "查询全部",operDesc = "查询全部")
    public List<NoticeDTO> selectByQuery(@RequestBody NoticeQuery noticeQuery) {
        return noticeService.selectByNoticeQuery(noticeQuery);
    }

    @PostMapping("/addNotice")
    @ApiOperation("添加通知")
    @OperationLogAnnotation(operModul = "通知管理模块-添加通知",operType = "添加通知",operDesc = "添加通知")
    public String addNotice(@RequestBody @Valid AddNoticeDTO addNoticeDTO) {
        if (addNoticeDTO.getId() != null)
            addNoticeDTO.setId(null);
        return noticeService.addNotice(addNoticeDTO);
    }

    @DeleteMapping("/deleteNotice")
    @ApiOperation("删除通知")
    @OperationLogAnnotation(operModul = "通知管理模块-删除通知",operType = "删除通知",operDesc = "删除通知")
    public String deleteNotice(Long id) {
        if (id == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        return noticeService.deleteNotice(id);
    }

    @PostMapping("/updateNotice")
    @ApiOperation("修改通知")
    @OperationLogAnnotation(operModul = "通知管理模块-修改通知",operType = "修改通知",operDesc = "修改通知")
    public String updateNotice(@RequestBody @Valid Notice notice) {
        if (notice == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        return noticeService.updateNotice(notice);
    }

}
