package com.fjp.mapper.pet;

import com.fjp.pojo.dto.pet.AddCaseRecordDTO;
import com.fjp.pojo.dto.pet.CaseRecordDTO;
import com.fjp.pojo.entity.pet.CaseRecord;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CaseRecordMapper extends Mapper<CaseRecord> {
    List<CaseRecordDTO> findAll(Long id);

    Integer updateCase(AddCaseRecordDTO addCaseRecordDTO);
}
