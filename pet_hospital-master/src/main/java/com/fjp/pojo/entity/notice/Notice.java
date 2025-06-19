package com.fjp.pojo.entity.notice;

import com.fjp.pojo.entity.BaseEntity;
import lombok.*;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@Table(name = "notice")
@NoArgsConstructor
@AllArgsConstructor
public class Notice extends BaseEntity {

    /**
     * 通知人id
     */
    private Long userId;

    /**
     * 通知描述
     */
    @NotNull
    private String description;
    /**
     * 标题
     */
    @NotNull
    private String title;
}
