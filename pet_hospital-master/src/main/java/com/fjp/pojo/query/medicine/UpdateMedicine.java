package com.fjp.pojo.query.medicine;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Data
public class UpdateMedicine {
    @NotNull
    private Long id;

    @NotNull
    private Integer number;

    private Long petId;
}
