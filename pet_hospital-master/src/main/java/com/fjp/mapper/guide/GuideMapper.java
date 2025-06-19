package com.fjp.mapper.guide;

import com.fjp.pojo.dto.guide.GuideDTO;
import com.fjp.pojo.entity.guide.Guide;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GuideMapper extends Mapper<Guide> {
    Guide selectById(Long id);
    List<GuideDTO> selectByGuideQuery(Guide guide);
    Integer addGuide(Guide guide);
    Integer deleteGuide(Long id);
    Integer updateGuide(Guide guide);
}
