package com.fjp.pojo.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.doctor.Doctor;
import com.fjp.pojo.entity.pet.Pet;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
public class AppointmentDTO extends BaseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long doctorId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long petId;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date appointDate;
    private String status;
    private Double money;
    private User user;
    private Doctor doctor;
    private Pet pet;
}
