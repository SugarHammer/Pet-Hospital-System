package com.fjp.service.impl.basedata;

import com.fjp.mapper.basedata.FormMapper;
import com.fjp.service.basedata.FormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("formService")
public class FormServiceImpl implements FormService {
    @Autowired
    private FormMapper formMapper;

    @Override
    public List<Map<String, Object>> petPercentage() {
        return formMapper.petPercentage();
    }

    @Override
    public List<Map<String, Object>> appointmentCount() {
        List<Map<String, Object>> list = formMapper.appointmentCount();
        Map<Object, Object> temp = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        list.forEach(action -> temp.put(sdf.format(action.get("name")), action.get("y")));
        list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < 7; i++) {
            calendar.add(Calendar.DATE, 1);
            HashMap<String, Object> map = new HashMap<>();
            String dateStr = sdf.format(calendar.getTime());
            map.put("name", dateStr);
            map.put("y", temp.getOrDefault(dateStr, 0));
            list.add(map);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> healthCount() {
        List<Map<String, Object>> list = formMapper.healthCount();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        List<Map<String, Object>> maps = formMapper.healthCount();

        return list;
    }
}
