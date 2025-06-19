package com.fjp.service.impl.guide;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.guide.GuideMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.guide.AddGuideDTO;
import com.fjp.pojo.dto.guide.GuideDTO;
import com.fjp.pojo.entity.guide.Guide;
import com.fjp.pojo.query.guide.GuideQuery;
import com.fjp.service.guide.GuideService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("guideService")
public class GuideServiceImpl implements GuideService {
    @Autowired
    private GuideMapper guideMapper;

    @Override
    public GuideDTO selectById(Long id) {
        Guide guide = guideMapper.selectById(id);
        if (guide == null) {
            throw new BusinessException(ErrorCode.GUIDE_ID_NOT_FOUND);
        }
        GuideDTO copy = BeanUtils.copy(guide, GuideDTO.class);
        return copy;
    }

    @Override
    @Pagination
    public List<GuideDTO> selectByGuideQuery(GuideQuery guideQuery) {
        if (guideQuery == null)
            throw new BusinessException(ErrorCode.NOTICE_NOT_FOUND);
        if (guideQuery.getTitle() != null)
            guideQuery.setTitle(guideQuery.getTitle().trim());
        if (guideQuery.getDescription() != null)
            guideQuery.setDescription(guideQuery.getDescription().trim());
        Guide copy = BeanUtils.copy(guideQuery, Guide.class);
        List<GuideDTO> guideDTOS = guideMapper.selectByGuideQuery(copy);
        return guideDTOS;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addGuide(AddGuideDTO addGuideDTO) {
        Guide copy = BeanUtils.copy(addGuideDTO, Guide.class);
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        Long id = user.getId();
        copy.setUserId(id);
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Integer integer = guideMapper.addGuide(copy);
        return integer == 1 ? "发布成功" : "发布失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteGuide(Long id) {
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        if (id == null) {
            throw new BusinessException(ErrorCode.NOTICE_ID_NOT_FOUND);
        }
        Integer integer = guideMapper.deleteGuide(id);
        return integer == 1 ? "删除成功" : "删除失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateGuide(Guide guide) {
        Boolean aBoolean = checkAdmin();
        if (!aBoolean)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Integer integer = guideMapper.updateGuide(guide);
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
