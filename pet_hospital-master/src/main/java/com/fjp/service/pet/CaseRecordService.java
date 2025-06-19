package com.fjp.service.pet;

import com.fjp.pojo.dto.pet.AddCaseRecordDTO;
import com.fjp.pojo.dto.pet.CaseRecordDTO;

import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案service
 */
public interface CaseRecordService {
    List<CaseRecordDTO> findAll(Long id);

    String addCaseRecord(AddCaseRecordDTO caseRecordDTO);

    String deleteCaseRecord(Long id);

    AddCaseRecordDTO findCaseRecordById(Long id);

    String updateCaseRecord(AddCaseRecordDTO addCaseRecordDTO);
}
