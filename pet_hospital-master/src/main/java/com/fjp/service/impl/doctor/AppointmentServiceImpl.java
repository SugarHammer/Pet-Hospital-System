package com.fjp.service.impl.doctor;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.config.annotation.Pagination;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.mapper.basedata.UserMapper;
import com.fjp.mapper.doctor.AppointmentMapper;
import com.fjp.mapper.doctor.DoctorMapper;
import com.fjp.mapper.pet.PetMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.doctor.AppointmentDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.doctor.Appointment;
import com.fjp.pojo.entity.doctor.Doctor;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.doctor.AppointmentQuery;
import com.fjp.service.doctor.AppointmentService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import com.github.pagehelper.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("appointmentService")
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentMapper appointmentMapper;
    @Autowired
    private PetMapper petMapper;
    @Autowired
    private DoctorMapper doctorMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    @Pagination
    public List<AppointmentDTO> commonQuery(AppointmentQuery appointmentQuery) {
        String doctorName = appointmentQuery.getDoctorName();
        String userName = appointmentQuery.getUserName();
        String petName = appointmentQuery.getPetName();
        if (doctorName == null) {
            doctorName = "";
        }
        if (userName == null) {
            userName = "";
        }
        if (petName == null) {
            petName = "";
        }
        UserDTO user = (UserDTO) BaseContextHolder.get("user");
        Long userId = null;
        List<RoleDTO> roleDTOList = user.getRoleDTOList();
        for (RoleDTO roleDTO : roleDTOList) {
            if ("客户".equals(roleDTO.getName())) {
                userId = user.getId();
                break;
            }
        }
        List<Appointment> appointments = appointmentMapper.findByUserNameOrDoctorName(userName, doctorName, petName, userId);
        Page<Appointment> page = (Page<Appointment>) appointments;
        return GlobalUtils.toDTOPage(page, this::toAppointmentDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.ADD)
    public String addAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = BeanUtils.copy(appointmentDTO, Appointment.class);
        UserDTO userDTO = (UserDTO) BaseContextHolder.get("user");
        appointment.setUserId(userDTO.getId());
        appointment.setStatus("等待审核");
        appointmentMapper.insert(appointment);
        return "添加成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @AutoFieldSuffer(AutoFieldType.UPDATE)
    public String updateAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = BeanUtils.copy(appointmentDTO, Appointment.class);
        checkIdExist(appointmentDTO, appointment);
        if ("同意".equals(appointment.getStatus())) {
            Example example = new Example(Appointment.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("status", "同意");
            criteria.andEqualTo("doctorId", appointment.getDoctorId());
            criteria.andEqualTo("appointDate", appointment.getAppointDate());
            List<Appointment> appointments = appointmentMapper.selectByExample(example);
            if (appointments.size() > 20) {
                throw new BusinessException(ErrorCode.EVERYDAY_APPOINTMENT_COUNT_EXCEED_20);
            }
        }
        appointmentMapper.updateByPrimaryKeySelective(appointment);
        return "审核成功";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deleteAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = BeanUtils.copy(appointmentDTO, Appointment.class);
        BusinessException exception = new BusinessException(ErrorCode.APPOINTMENT_ID_NOT_FOUND);
        GlobalUtils.checkExistId(appointment, appointmentMapper, exception);
        appointmentMapper.deleteByPrimaryKey(appointmentDTO.getId());
        return "取消预约成功";
    }

    private AppointmentDTO toAppointmentDTO(Appointment appointment) {
        AppointmentDTO appointmentDTO = BeanUtils.copy(appointment, AppointmentDTO.class);
        Pet pet = petMapper.selectByPrimaryKey(appointment.getPetId());
        User user = userMapper.selectByPrimaryKey(appointment.getUserId());
        Doctor doctor = doctorMapper.selectByPrimaryKey(appointment.getDoctorId());
        appointmentDTO.setPet(pet);
        appointmentDTO.setUser(user);
        appointmentDTO.setDoctor(doctor);
        return appointmentDTO;
    }

    private void checkIdExist(AppointmentDTO appointmentDTO, Appointment appointment) {
        BusinessException appointmentIdNotExist = new BusinessException(ErrorCode.APPOINTMENT_ID_NOT_FOUND);
        BusinessException userIdNotExist = new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
        BusinessException doctorIdNotExist = new BusinessException(ErrorCode.DOCTOR_ID_NOT_FOUND);
        BusinessException petIdNotExist = new BusinessException(ErrorCode.PET_ID_NOT_FOUND);
        GlobalUtils.checkExistId(appointment, appointmentMapper, appointmentIdNotExist);
        User user = new User();
        user.setId(appointmentDTO.getUserId());
        GlobalUtils.checkExistId(user, userMapper, userIdNotExist);
        Doctor doctor = new Doctor();
        doctor.setId(appointmentDTO.getDoctorId());
        GlobalUtils.checkExistId(doctor, doctorMapper, doctorIdNotExist);
        Pet pet = new Pet();
        pet.setId(appointmentDTO.getPetId());
        if (petMapper.selectByPrimaryKey(pet.getId()) == null) {
            throw petIdNotExist;
        }
    }
}
