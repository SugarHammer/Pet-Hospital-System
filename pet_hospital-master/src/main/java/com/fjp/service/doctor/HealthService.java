package com.fjp.service.doctor;

import com.fjp.pojo.dto.doctor.HealthDTO;
import com.fjp.pojo.query.doctor.HealthQuery;

import java.util.List;

public interface HealthService {
    List<HealthDTO> commonQuery(HealthQuery healthQuery);

    String addHealth(HealthDTO healthDTO);

    String updateHealth(HealthDTO healthDTO);

    String deleteHealth(HealthDTO healthDTO);
}
