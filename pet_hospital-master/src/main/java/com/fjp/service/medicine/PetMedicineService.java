package com.fjp.service.medicine;

import com.fjp.pojo.dto.medicine.PetMedicineDTO;
import com.fjp.pojo.query.medicine.PetMedicineQuery;

import java.util.List;

public interface PetMedicineService {
    Boolean add(PetMedicineDTO petMedicineDTO);

    List<PetMedicineDTO> getAll(PetMedicineQuery petMedicineQuery);
}
