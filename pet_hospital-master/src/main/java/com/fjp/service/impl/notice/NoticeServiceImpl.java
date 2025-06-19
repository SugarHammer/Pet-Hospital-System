package com.fjp.service.impl.notice;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.notice.NoticeMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.notice.AddNoticeDTO;
import com.fjp.pojo.dto.notice.NoticeDTO;
import com.fjp.pojo.entity.notice.Notice;
import com.fjp.pojo.query.notice.NoticeQuery;
import com.fjp.service.notice.NoticeService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public NoticeDTO selectById(Long id) {
        Notice notice = noticeMapper.selectById(id);
        if (notice == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        NoticeDTO copy = BeanUtils.copy(notice, NoticeDTO.class);
        return copy;
    }

    @Override
    @Pagination
    public List<NoticeDTO> selectByNoticeQuery(NoticeQuery noticeQuery) {
        if (noticeQuery == null)
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        if (noticeQuery.getDescription() != null)
            noticeQuery.setDescription(noticeQuery.getDescription().trim());
        if (noticeQuery.getTitle() != null)
            noticeQuery.setTitle(noticeQuery.getTitle().trim());
        Notice copy = BeanUtils.copy(noticeQuery, Notice.class);
        List<NoticeDTO> noticeDTOS = noticeMapper.selectByNoticeQuery(copy);
        return noticeDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addNotice(AddNoticeDTO addNoticeDTO) {
        Notice copy = BeanUtils.copy(addNoticeDTO, Notice.class);
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        Long id = user.getId();
        copy.setUserId(id);
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Integer integer = noticeMapper.addNotice(copy);
        return integer == 1 ? "发布成功" : "发布失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteNotice(Long id) {
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        if (id == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        Integer integer = noticeMapper.deleteNotice(id);
        return integer == 1 ? "删除成功" : "删除失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateNotice(Notice notice) {
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Integer integer = noticeMapper.updateNotice(notice);
        return integer == 1 ? "修改成功" : "修改失败";
    }

    /**
     * 判断是否是管理员
     *
     * @return
     */
    private Boolean checkAdmin() {
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        if (user == null)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        List<RoleDTO> roleDTOList = user.getRoleDTOList();
        for (RoleDTO roleDTO : roleDTOList) {
            if (roleDTO.getName().equals("管理员")) {
                return true;
            }
        }
        return false;
    }
}
