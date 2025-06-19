package com.fjp.pojo.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class DoctorDTO extends BaseDTO {
    @NotEmpty
    private String name;
    @NotEmpty
    private String sex;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @NotNull
    private Date birth;
    @NotEmpty
    private String title;
    @NotEmpty
    private String department;
    @Pattern(regexp = "^1[3-9][0-9]{9}$")
    private String phone;
    @NotEmpty
    private String doctorPracticeCertificateNo;
    @NotEmpty
    private String education;
    @Pattern(regexp = "^[1-9][0-9]{5}(?:18|19|20)[0-9]{2}(?:0[0-9]|10|11|12)(?:0[1-9]|[1-2][0-9]|30|31)[0-9]{3}[0-9Xx]$")
    private String idCard;
    @NotEmpty
    private String address;
}
