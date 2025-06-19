package com.fjp.service.guide;

import com.fjp.pojo.dto.guide.AddGuideDTO;
import com.fjp.pojo.dto.guide.GuideDTO;
import com.fjp.pojo.entity.guide.Guide;
import com.fjp.pojo.query.guide.GuideQuery;

import java.util.List;

public interface GuideService {
    /**
     * 查看发布详情
     * @param id
     * @return
     */
    GuideDTO selectById(Long id);

    /**
     * 查询指南
     * @param guideQuery
     * @return
     */
    List<GuideDTO> selectByGuideQuery(GuideQuery guideQuery);

    /**
     * 增加指南
     * @param
     * @return
     */
    String addGuide(AddGuideDTO addGuideDTO);

    /**
     * 删除指南
     * @param id
     * @return
     */
    String deleteGuide(Long id);

    /**
     * 插入通知
     * @param guide
     * @return
     */
    String updateGuide(Guide guide);
}
