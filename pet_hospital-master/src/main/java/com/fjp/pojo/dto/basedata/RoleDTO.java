package com.fjp.pojo.dto.basedata;

import com.fjp.pojo.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class RoleDTO extends BaseDTO {
    @NotEmpty(message = "角色名称不能为空")
    private String name;
    private List<ResourceDTO> resourceDTOList;
    private long[] resourceIds;
}
