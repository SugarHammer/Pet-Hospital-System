package com.fjp.service.doctor;

import com.fjp.pojo.dto.doctor.DoctorDTO;
import com.fjp.pojo.query.doctor.DoctorQuery;

import java.util.List;

public interface DoctorService {
    List<DoctorDTO> commonQuery(DoctorQuery doctorQuery);

    String addDoctor(DoctorDTO doctorDTO);

    String updateDoctor(DoctorDTO doctorDTO);

    String deleteDoctor(DoctorDTO doctorDTO);
}
