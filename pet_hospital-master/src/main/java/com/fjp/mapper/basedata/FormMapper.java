package com.fjp.mapper.basedata;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FormMapper {

    List<Map<String, Object>> petPercentage();

    List<Map<String, Object>> appointmentCount();

    List<Map<String, Object>> healthCount();
}
