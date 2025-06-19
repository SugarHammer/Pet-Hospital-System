package com.fjp.pojo.entity.pet;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import com.fjp.pojo.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Table;
import java.util.Date;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案实体类
 */
@EqualsAndHashCode(callSuper = true)
@Table(name = "pet")
@Data
@ToString(callSuper = true)
public class Pet extends BaseEntity {
    /**
     * 客户ID
     */
    //@JsonFormat(shape = JsonFormat.Shape.STRING)//避免返回给前端造成溢出
    private Long userId;

    /**
     * 宠物名称
     */
    private String name;

    /**
     * 宠物种类
     */
    private String species;

    /**
     * 宠物品种
     */
    private String varieties;

    /**
     * 出生年月
     */
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date birth;

    /**
     * 身高
     */
    private Double height;

    /**
     * 体重
     */
    private Double weight;

    /**
     * 图片
     */
    private String pictureUrl;

    /**
     * 备注
     */
    private String remark;

    /**
     * 住院
     */
    private Integer inHospital;
}
