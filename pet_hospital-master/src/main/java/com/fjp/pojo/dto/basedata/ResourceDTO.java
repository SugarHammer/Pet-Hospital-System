package com.fjp.pojo.dto.basedata;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class ResourceDTO extends BaseDTO {
    private String name;
    private String url;
    private String src;
    private String icon;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long parentId;
    private Integer type;
    private List<ResourceDTO> children;
}
