package com.fjp.mapper.doctor;

import com.fjp.pojo.entity.doctor.Health;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface HealthMapper extends Mapper<Health> {
    List<Health> findByUserNameOrPetName(String userName, String petName, Long userId);
}
