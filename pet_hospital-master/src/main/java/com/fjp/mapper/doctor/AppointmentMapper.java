package com.fjp.mapper.doctor;

import com.fjp.pojo.entity.doctor.Appointment;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AppointmentMapper extends Mapper<Appointment> {
    List<Appointment> findByUserNameOrDoctorName(String userName, String doctorName, String petName, Long userId);
}
