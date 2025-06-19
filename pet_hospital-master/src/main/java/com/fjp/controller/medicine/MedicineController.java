package com.fjp.controller.medicine;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.medicine.MedicineDTO;
import com.fjp.pojo.dto.medicine.PetMedicineDTO;
import com.fjp.pojo.entity.medicine.Medicine;
import com.fjp.pojo.query.medicine.MedicineQuery;
import com.fjp.pojo.query.medicine.PetMedicineQuery;
import com.fjp.pojo.query.medicine.UpdateMedicine;
import com.fjp.service.medicine.MedicineService;
import com.fjp.service.medicine.PetMedicineService;
import com.fjp.util.BeanUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/medicine")
@Api("药品管理")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PetMedicineService petMedicineService;

    @PostMapping("/getAll")
    @ApiOperation("查询全部药品")
    @OperationLogAnnotation(operModul = "药品管理模块-查询全部药品",operType = "查询全部药品",operDesc = "查询全部药品")
    public List<MedicineDTO> findAll(@RequestBody MedicineQuery medicineQuery) {
        List<Medicine> all = medicineService.findAll(medicineQuery);
        List<MedicineDTO> dtos = new ArrayList<>();
        for (Medicine medicine : all) {
            dtos.add(BeanUtils.copy(medicine, MedicineDTO.class));
        }
        return dtos;
    }

    @PostMapping("/petMedicine/getAll")
    @ApiOperation("查询全部宠物药品记录")
    @OperationLogAnnotation(operModul = "药品管理模块-查询全部宠物药品记录",operType = "查询全部宠物药品记录",operDesc = "查询全部宠物药品记录")
    public List<PetMedicineDTO> findAllMedicine(@RequestBody PetMedicineQuery petMedicineQuery) {
        return petMedicineService.getAll(petMedicineQuery);
    }

    @PostMapping("/getMedicine")
    @ApiOperation("取药")
    @OperationLogAnnotation(operModul = "药品管理模块-取药",operType = "取药",operDesc = "取药")
    public String getMedicine(@RequestBody @Valid UpdateMedicine updateMedicine) {
        if (updateMedicine.getNumber() <= 0)
            throw new BusinessException(ErrorCode.MEDICINE_NUMBER_WRONG);
        if (updateMedicine.getPetId() == null)
            throw new BusinessException(ErrorCode.PET_ID_NOT_FOUND);
        return medicineService.update(updateMedicine.getId(), updateMedicine.getNumber(), true, updateMedicine.getPetId());
    }

    @PostMapping("/enterMedicine")
    @ApiOperation("入库")
    @OperationLogAnnotation(operModul = "药品管理模块-入库",operType = "入库",operDesc = "入库")
    public String enterMedicine(@RequestBody @Valid UpdateMedicine updateMedicine) {
        if (updateMedicine.getNumber() <= 0)
            throw new BusinessException(ErrorCode.MEDICINE_NUMBER_WRONG);
        return medicineService.update(updateMedicine.getId(), updateMedicine.getNumber(), false, null);
    }

    @PostMapping("/add")
    @ApiOperation("添加药品")
    @OperationLogAnnotation(operModul = "药品管理模块-添加药品",operType = "添加药品",operDesc = "添加药品")
    public String add(@RequestBody @Valid MedicineDTO medicineDTO) {
        return medicineService.add(medicineDTO);
    }
}
