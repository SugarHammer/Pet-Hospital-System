package com.fjp.pojo.assembler.pet;

import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.pet.PetQuery;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案转换类
 */
public class PetAssembler {
    public static Pet QueryToEntity(PetQuery petQuery) {
        Pet pet = new Pet();
        pet.setId(petQuery.getId());
        if (petQuery.getName() != null)
            petQuery.setName(petQuery.getName().trim());
        pet.setName(petQuery.getName());
        pet.setAddTime(petQuery.getAddTime());
        if (petQuery.getVarieties() != null)
            petQuery.setVarieties(petQuery.getVarieties().trim());
        pet.setVarieties(petQuery.getVarieties());
        if (petQuery.getSpecies() != null)
            petQuery.setSpecies(petQuery.getSpecies().trim());
        pet.setSpecies(petQuery.getSpecies());
        pet.setUserId(petQuery.getFindID());
        pet.setInHospital(petQuery.getInHospital());
        return pet;
    }

    public static PetDTO EntityToDTO(Pet pet, String userName) {
        return PetDTO.builder().name(pet.getName())
                .id(pet.getId())
                .addTime(pet.getAddTime())
                .height(pet.getHeight())
                .inHospital(pet.getInHospital())
                .picturelURL(pet.getPictureUrl())
                .species(pet.getSpecies())
                .userName(userName)
                .weight(pet.getWeight())
                .varieties(pet.getVarieties())
                .build();
    }
}
