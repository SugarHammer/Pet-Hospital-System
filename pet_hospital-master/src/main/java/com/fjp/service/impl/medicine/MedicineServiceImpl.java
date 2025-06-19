package com.fjp.service.impl.medicine;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.medicine.MedicineMapper;
import com.fjp.pojo.dto.medicine.MedicineDTO;
import com.fjp.pojo.dto.medicine.PetMedicineDTO;
import com.fjp.pojo.entity.doctor.Doctor;
import com.fjp.pojo.entity.medicine.Medicine;
import com.fjp.pojo.query.medicine.MedicineQuery;
import com.fjp.service.medicine.MedicineService;
import com.fjp.service.medicine.PetMedicineService;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("medicineService")
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineMapper medicineMapper;

    @Autowired
    private PetMedicineService petMedicineService;

    @Override
    @Pagination
    public List<Medicine> findAll(MedicineQuery medicineQuery) {
        String name = medicineQuery.getName();
        String type = medicineQuery.getType();
        Example example = new Example(Medicine.class);
        Example.Criteria criteria = example.createCriteria();
        if (name != null && !name.trim().equals(""))
            criteria.andLike("name", "%"+name.trim()+"%");
        if (type != null && !type.trim().equals(""))
            criteria.andLike("type", "%"+type.trim()+"%");
        List<Medicine> medicines = medicineMapper.selectByExample(example);
        return medicines;
    }

    /**
     *
     * @param id
     * @param number
     * @param b true 取药 false 入库
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String update(Long id, Integer number, Boolean b, Long petId) {
        Medicine medicine1 = new Medicine();
        medicine1.setId(id);
        Medicine medicine = medicineMapper.selectByPrimaryKey(medicine1);
        if (medicine == null)
            throw new BusinessException(ErrorCode.MEDICINE_NOT_FIND);
        if (b) {
            number = medicine.getNumber() - number;
        }
        else
            number = medicine.getNumber() + number;
        if (number < 0)
            throw new BusinessException(ErrorCode.MEDICINE_NUMBER_WRONG);
        medicine1.setNumber(number);
        int i = medicineMapper.updateByPrimaryKeySelective(medicine1);
        Boolean add = true;
        if (b) {
            PetMedicineDTO petMedicineDTO = new PetMedicineDTO();
            petMedicineDTO.setPetId(petId);
            petMedicineDTO.setMedicineId(id);
            petMedicineDTO.setNumber(number);
            add = petMedicineService.add(petMedicineDTO);
        }
        if (i==1 && add)
            return "成功";
        else
            return "失败";
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    @Transactional(rollbackFor = Exception.class)
    public String add(MedicineDTO medicineDTO) {
        Medicine copy = BeanUtils.copy(medicineDTO, Medicine.class);
        int insert = medicineMapper.insert(copy);
        return insert==1?"添加成功":"添加失败";
    }
}
