package com.fjp.pojo.dto.basedata;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdatePasswordDTO {
    private String oldPassword;
    @Pattern(message = "请输入6到12位的字符", regexp = "^[a-zA-Z0-9_-]{6,12}$")
    private String newPassword;
}
