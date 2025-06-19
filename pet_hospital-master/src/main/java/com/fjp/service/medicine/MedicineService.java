package com.fjp.service.medicine;

import com.fjp.pojo.dto.medicine.MedicineDTO;
import com.fjp.pojo.entity.medicine.Medicine;
import com.fjp.pojo.query.medicine.MedicineQuery;

import java.util.List;

public interface MedicineService {
    List<Medicine> findAll(MedicineQuery medicineQuery);

    String update(Long id, Integer number, Boolean b, Long petId);

    String add(MedicineDTO medicineDTO);
}
