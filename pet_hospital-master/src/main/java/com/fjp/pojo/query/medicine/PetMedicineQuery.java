package com.fjp.pojo.query.medicine;

import com.fjp.pojo.query.PageQuery;
import lombok.Data;

@Data
public class PetMedicineQuery extends PageQuery {
    private Long petId;

    private Long medicineId;
}
