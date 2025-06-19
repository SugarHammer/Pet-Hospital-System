package com.fjp.pojo.dto.medicine;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fjp.pojo.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class PetMedicineDTO extends BaseDTO {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long petId;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long medicineId;

    @NotNull
    private Integer number;

    @NotNull
    private String remark;
}
