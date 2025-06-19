package com.fjp.mapper.notice;

import com.fjp.pojo.dto.notice.NoticeDTO;
import com.fjp.pojo.entity.notice.Notice;
import com.fjp.pojo.query.basedata.UserQuery;
import com.fjp.pojo.query.notice.NoticeQuery;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NoticeMapper extends Mapper<Notice> {
    Notice selectById(Long id);
    List<NoticeDTO> selectByNoticeQuery(Notice notice);
    Integer addNotice(Notice notice);
    Integer deleteNotice(Long id);
    Integer updateNotice(Notice notice);
}
