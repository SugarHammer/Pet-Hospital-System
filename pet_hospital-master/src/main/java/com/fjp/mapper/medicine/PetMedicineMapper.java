package com.fjp.mapper.medicine;

import com.fjp.pojo.entity.medicine.PetMedicine;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

@Repository
public interface PetMedicineMapper extends BaseMapper<PetMedicine> {
}
