package com.fjp.service.impl.doctor;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.doctor.DoctorMapper;
import com.fjp.pojo.dto.doctor.DoctorDTO;
import com.fjp.pojo.entity.doctor.Doctor;
import com.fjp.pojo.query.doctor.DoctorQuery;
import com.fjp.service.doctor.DoctorService;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;

@Service("doctorService")
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorMapper doctorMapper;

    @Override
    @Pagination
    public List<DoctorDTO> commonQuery(DoctorQuery doctorQuery) {
        Example example = new Example(Doctor.class);
        Example.Criteria criteria = example.createCriteria();
        if (doctorQuery.getPhone() != null) {
            criteria.orLike("phone", "%" + doctorQuery.getPhone().trim() + "%");
        }
        if (doctorQuery.getName() != null) {
            criteria.orLike("name", "%" + doctorQuery.getName().trim() + "%");
        }
        if (doctorQuery.getIdCard() != null) {
            criteria.orLike("idCard", "%" + doctorQuery.getIdCard().trim() + "%");
        }
        List<Doctor> doctors = doctorMapper.selectByExample(example);
        if (!(doctors instanceof Page)) {
            throw new BusinessException(ErrorCode.PAGE_SEARCH_EXCEPTION);
        }
        return GlobalUtils.toDTOPage((Page<Doctor>) doctors, doctor -> BeanUtils.copy(doctor, DoctorDTO.class));
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = BeanUtils.copy(doctorDTO, Doctor.class);
        HashMap<String, BusinessException> map = new HashMap<>();
        map.put("idCard", new BusinessException(ErrorCode.IDCARD_ALREADY_USED));
        map.put("phone", new BusinessException(ErrorCode.PHONE_ALREADY_USED));
        GlobalUtils.checkColumnExist(doctor, map, doctorMapper, false);
        doctorMapper.insert(doctor);
        return "添加成功";
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = BeanUtils.copy(doctorDTO, Doctor.class);
        GlobalUtils.checkExistId(doctor, doctorMapper, new BusinessException(ErrorCode.DOCTOR_ID_NOT_FOUND));
        HashMap<String, BusinessException> map = new HashMap<>();
        map.put("idCard", new BusinessException(ErrorCode.IDCARD_ALREADY_USED));
        map.put("phone", new BusinessException(ErrorCode.PHONE_ALREADY_USED));
        GlobalUtils.checkColumnExist(doctor, map, doctorMapper, true);
        doctorMapper.updateByPrimaryKeySelective(doctor);
        return "修改成功";
    }

    @Override
    public String deleteDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = BeanUtils.copy(doctorDTO, Doctor.class);
        GlobalUtils.checkExistId(doctor, doctorMapper, new BusinessException(ErrorCode.DOCTOR_ID_NOT_FOUND));
        doctorMapper.deleteByPrimaryKey(doctor);
        return "删除成功";
    }
}
