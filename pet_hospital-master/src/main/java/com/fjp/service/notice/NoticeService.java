package com.fjp.service.notice;

import com.fjp.pojo.dto.notice.AddNoticeDTO;
import com.fjp.pojo.dto.notice.NoticeDTO;
import com.fjp.pojo.entity.notice.Notice;
import com.fjp.pojo.query.notice.NoticeQuery;

import java.util.List;

public interface NoticeService {
    /**
     * 查看通知详情
     * @param id
     * @return
     */
    NoticeDTO selectById(Long id);


    /**
     * 查询通知
     * @param noticeQuery
     * @return
     */
    List<NoticeDTO> selectByNoticeQuery(NoticeQuery noticeQuery);

    /**
     * 插入通知
     * @param addNoticeDTO
     * @return
     */
    String addNotice(AddNoticeDTO addNoticeDTO);

    /**
     * 删除通知
     * @param id
     * @return
     */
    String deleteNotice(Long id);

    /**
     * 插入通知
     * @param notice
     * @return
     */
    String updateNotice(Notice notice);
}
