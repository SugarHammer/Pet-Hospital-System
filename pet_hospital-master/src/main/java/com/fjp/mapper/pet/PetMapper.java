package com.fjp.mapper.pet;

import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.pet.PetQuery;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案mapper
 */
@Repository
public interface PetMapper extends BaseMapper<Pet> {
    /**
     * 查询全部宠物
     * @param petQuery
     * @return
     */
    List<Pet> findAll(PetQuery petQuery);
}
