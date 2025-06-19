package com.fjp.service.pet;


import com.fjp.pojo.dto.pet.AddPetDto;
import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.query.pet.PetQuery;

import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案service
 */
public interface PetService {
    /**
     * 分页查询全部宠物信息
     * @param petQuery
     * @return
     */
    List<PetDTO> findPets(PetQuery petQuery);

    /**
     * 查询全部宠物信息
     * @param petQuery
     * @return
     */
    List<PetDTO> findPetsNoPage(PetQuery petQuery);

    /**
     * 添加宠物档案
     * @param addPetDto
     * @return
     */
    String addPet(AddPetDto addPetDto);

    /**
     * 通过id查询宠物档案
     * @param id
     * @return
     */
    AddPetDto findPetDtoById(Long id);

    /**
     * 更新宠物档案
     * @param addPetDto
     * @return
     */
    String updatePet(AddPetDto addPetDto);

    /**
     * 删除宠物档案
     * @param id
     * @return
     */
    String deletePet(Long id);
}
