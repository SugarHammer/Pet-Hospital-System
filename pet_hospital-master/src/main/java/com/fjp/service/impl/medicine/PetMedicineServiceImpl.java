package com.fjp.service.impl.medicine;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.medicine.MedicineMapper;
import com.fjp.mapper.medicine.PetMedicineMapper;
import com.fjp.mapper.pet.PetMapper;
import com.fjp.pojo.dto.medicine.PetMedicineDTO;
import com.fjp.pojo.entity.medicine.Medicine;
import com.fjp.pojo.entity.medicine.PetMedicine;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.medicine.PetMedicineQuery;
import com.fjp.service.medicine.PetMedicineService;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Service("petMedicineService")
public class PetMedicineServiceImpl implements PetMedicineService {
    @Autowired
    private PetMedicineMapper petMedicineMapper;

    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private PetMapper petMapper;

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    public Boolean add(PetMedicineDTO petMedicineDTO) {
        Long medicineId = petMedicineDTO.getMedicineId();
        Long petId = petMedicineDTO.getPetId();
        Medicine medicine = new Medicine();
        medicine.setId(medicineId);
        Medicine medicine1 = medicineMapper.selectByPrimaryKey(medicine);
        if (medicine1 == null) {
            throw new BusinessException(ErrorCode.MEDICINE_NUMBER_WRONG);
        }
        Pet pet = new Pet();
        pet.setId(petId);
        Pet pet1 = petMapper.selectByPrimaryKey(pet);
        if (pet1 == null) {
            throw new BusinessException(ErrorCode.PET_ID_NOT_FOUND);
        }
        PetMedicine copy = BeanUtils.copy(petMedicineDTO, PetMedicine.class);

        int i = petMedicineMapper.insertSelective(copy);
        return i==1;
    }

    @Override
    @Pagination
    public List<PetMedicineDTO> getAll(PetMedicineQuery petMedicineQuery) {
        PetMedicine copy = BeanUtils.copy(petMedicineQuery, PetMedicine.class);
        List<PetMedicine> select = petMedicineMapper.select(copy);
        ArrayList<PetMedicineDTO> petMedicineDTOS = new ArrayList<>();
        for (PetMedicine petMedicine : select) {
            petMedicineDTOS.add(BeanUtils.copy(petMedicine, PetMedicineDTO.class));
        }
        return petMedicineDTOS;
    }
}
