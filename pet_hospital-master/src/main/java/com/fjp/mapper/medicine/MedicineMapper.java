package com.fjp.mapper.medicine;

import com.fjp.pojo.entity.medicine.Medicine;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface MedicineMapper extends Mapper<Medicine> {
}
