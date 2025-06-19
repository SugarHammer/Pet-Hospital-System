package com.fjp.service.impl.pet;


import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.doctor.DoctorMapper;
import com.fjp.mapper.pet.CaseRecordMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.pet.AddCaseRecordDTO;
import com.fjp.pojo.dto.pet.CaseRecordDTO;
import com.fjp.pojo.entity.doctor.Doctor;
import com.fjp.pojo.entity.pet.CaseRecord;
import com.fjp.service.pet.CaseRecordService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 
 * @create_time 2021-9-25
 * @describe 宠物病例Service Impl
 */
@Service("caseRecordService")
public class CaseRecordServiceImpl implements CaseRecordService {
    @Autowired
    private CaseRecordMapper caseRecordMapper;

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    public List<CaseRecordDTO> findAll(Long id) {
        return caseRecordMapper.findAll(id);
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    @Transactional(rollbackFor = Exception.class)
    public String addCaseRecord(AddCaseRecordDTO caseRecordDTO) {
        Long aLong = checkCustomer();
        if (aLong != null)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Doctor doctor = new Doctor();
        doctor.setId(caseRecordDTO.getDoctorId());
        Doctor doctor1 = doctorMapper.selectByPrimaryKey(doctor);
        if (doctor1 == null)
            throw new BusinessException(ErrorCode.DOCTOR_ID_NOT_FOUND);
        CaseRecord copy = BeanUtils.copy(caseRecordDTO, CaseRecord.class);
        int i = caseRecordMapper.insertSelective(copy);
        if (i == 0)
            return "添加失败";
        return "添加成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteCaseRecord(Long id) {
        Long aLong = checkCustomer();
        if (aLong != null)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        if (id == null)
            return "无法删除";
        CaseRecord caseRecord = new CaseRecord();
        caseRecord.setId(id);
        int i = caseRecordMapper.deleteByPrimaryKey(caseRecord);
        if (i==0)
            return "删除失败";
        return "删除成功";
    }

    @Override
    public AddCaseRecordDTO findCaseRecordById(Long id) {
        CaseRecord caseRecord = new CaseRecord();
        caseRecord.setId(id);
        CaseRecord caseRecord1 = caseRecordMapper.selectByPrimaryKey(caseRecord);
        return BeanUtils.copy(caseRecord1, AddCaseRecordDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateCaseRecord(AddCaseRecordDTO addCaseRecordDTO) {
        Long aLong = checkCustomer();
        if (aLong != null)
            throw new BusinessException(ErrorCode.AUTHORITY_NOT_FOUND);
        Doctor doctor = new Doctor();
        doctor.setId(addCaseRecordDTO.getDoctorId());
        Doctor doctor1 = doctorMapper.selectByPrimaryKey(doctor);
        if (doctor1 == null)
            throw new BusinessException(ErrorCode.DOCTOR_ID_NOT_FOUND);
        Integer integer = caseRecordMapper.updateCase(addCaseRecordDTO);
        return "更新成功";
    }

    /**
     * 判断是否是客户
     * @return 客户-id 其他-null
     */
    private Long checkCustomer() {
        Boolean flag = true;
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        if (user == null)
            throw new BusinessException(ErrorCode.ROLE_ID_NOT_FOUND);
        List<RoleDTO> roleDTOList = user.getRoleDTOList();
        for (RoleDTO roleDTO : roleDTOList) {
            if (roleDTO.getName().equals("客户")) {
                flag = false;
            }
        }
        if (flag)
            return null;
        else
            return user.getId();
    }
}
