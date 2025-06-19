package com.fjp.pojo.dto.pet;

import com.fjp.pojo.dto.BaseDTO;
import lombok.*;

/**
 * 
 * @create_time 2021-9-24
 * @describe 宠物病例DTO
 */
@Data
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRecordDTO extends BaseDTO {

    /**
     * 医生名称
     */
    private String doctorName;

    /**
     * 描述
     */
    private String description;
}
