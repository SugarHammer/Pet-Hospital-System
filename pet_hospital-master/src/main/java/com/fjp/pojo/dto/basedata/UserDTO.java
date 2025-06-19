package com.fjp.pojo.dto.basedata;

import com.fjp.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    @ApiModelProperty("用户名")
    @NotEmpty(message = "用户名不能为空")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    @NotEmpty(message = "姓名不能为空")
    private String name;

    @ApiModelProperty("性别")
    @NotEmpty(message = "性别不能为空")
    private String sex;

    @ApiModelProperty("身份证号")
    @Pattern(message = "请输入正确的身份证号", regexp = "^[1-9][0-9]{5}(?:18|19|20)[0-9]{2}(?:0[0-9]|10|11|12)(?:0[1-9]|[1-2][0-9]|30|31)[0-9]{3}[0-9Xx]$")
    private String idCard;

    @ApiModelProperty("手机号")
    @Pattern(message = "请输入正确的手机号", regexp = "^1[3-9][0-9]{9}$")
    private String phone;

    @ApiModelProperty("地址")
    @NotEmpty(message = "地址不能为空")
    private String address;

    @ApiModelProperty("状态")
    private Boolean status;

    @ApiModelProperty("拥有的资源")
    private List<ResourceDTO> resourceDTOList;

    @ApiModelProperty("拥有的角色")
    private List<RoleDTO> roleDTOList;
}
