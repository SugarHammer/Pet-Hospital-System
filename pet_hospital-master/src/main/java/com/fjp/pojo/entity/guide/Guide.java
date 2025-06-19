package com.fjp.pojo.entity.guide;

import com.fjp.pojo.entity.BaseEntity;
import lombok.*;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Table(name = "guide")
@NoArgsConstructor
@AllArgsConstructor
public class Guide extends BaseEntity {

    /**
     * 发布人id
     */
    private Long userId;

    /**
     * 指南描述
     */
    @NotNull
    private String description;
    /**
     * 指南标题
     */
    @NotNull
    private String title;
}
