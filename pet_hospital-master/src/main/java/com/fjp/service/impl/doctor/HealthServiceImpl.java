package com.fjp.service.impl.doctor;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.mapper.basedata.UserMapper;
import com.fjp.mapper.doctor.HealthMapper;
import com.fjp.mapper.pet.PetMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.doctor.HealthDTO;
import com.fjp.pojo.entity.doctor.Health;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.doctor.HealthQuery;
import com.fjp.service.doctor.HealthService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("healthService")
public class HealthServiceImpl implements HealthService {
    @Autowired
    private HealthMapper healthMapper;
    @Autowired
    private PetMapper petMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Pagination
    public List<HealthDTO> commonQuery(HealthQuery healthQuery) {
        String userName = healthQuery.getUserName();
        String petName = healthQuery.getPetName();
        if (userName == null) {
            userName = "";
        }
        if (petName == null) {
            petName = "";
        }
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        Long userId = null;
        List<RoleDTO> roleDTOList = user.getRoleDTOList();
        for (RoleDTO roleDTO : roleDTOList) {
            if ("客户".equals(roleDTO.getName())) {
                userId = user.getId();
                break;
            }
        }
        List<Health> healthList = healthMapper.findByUserNameOrPetName(userName, petName, userId);
        Page<Health> healthPage = (Page<Health>) healthList;
        return GlobalUtils.toDTOPage(healthPage, health -> {
            HealthDTO healthDTO = BeanUtils.copy(health, HealthDTO.class);
            Pet pet = petMapper.selectByPrimaryKey(health.getPetId());
            healthDTO.setPet(pet);
            healthDTO.setAddUser(userMapper.selectByPrimaryKey(health.getUserId()));
            healthDTO.setOwner(userMapper.selectByPrimaryKey(pet.getUserId()));
            return healthDTO;
        });
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addHealth(HealthDTO healthDTO) {
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        Long userId = user.getId();
        Health health = BeanUtils.copy(healthDTO, Health.class);
        health.setUserId(userId);
        healthMapper.insert(health);
        return "添加成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateHealth(HealthDTO healthDTO) {
        Health health = BeanUtils.copy(healthDTO, Health.class);
        healthMapper.updateByPrimaryKeySelective(health);
        return "修改成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteHealth(HealthDTO healthDTO) {
        Health health = BeanUtils.copy(healthDTO, Health.class);
        healthMapper.deleteByPrimaryKey(health);
        return "删除成功";
    }
}
