package com.fjp.service.doctor;

import com.fjp.pojo.dto.doctor.AppointmentDTO;
import com.fjp.pojo.query.doctor.AppointmentQuery;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTO> commonQuery(AppointmentQuery appointmentQuery);

    String addAppointment(AppointmentDTO appointmentDTO);

    String updateAppointment(AppointmentDTO appointmentDTO);

    String deleteAppointment(AppointmentDTO appointmentDTO);
}
