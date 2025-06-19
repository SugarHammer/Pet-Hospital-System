package com.fjp.mapper.doctor;

import com.fjp.pojo.entity.doctor.Doctor;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface DoctorMapper extends Mapper<Doctor> {
}
