package com.fjp.service.basedata;

import java.util.List;
import java.util.Map;

public interface FormService {
    List<Map<String, Object>> petPercentage();

    List<Map<String, Object>> appointmentCount();

    List<Map<String, Object>> healthCount();
}
