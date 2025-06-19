package com.fjp.controller.pet;

import com.fjp.config.annotation.OperationLogAnnotation;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.pet.AddPetDto;
import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.query.pet.PetQuery;
import com.fjp.service.pet.PetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案 controller
 */
@RestController
@RequestMapping("/archives")
@Api("宠物档案")
public class ArchivesController {
    @Autowired
    private PetService petService;

    @PostMapping("/findAll")
    @ApiOperation("查询全部宠物档案")
    @OperationLogAnnotation(operModul = "宠物档案模块-查询全部宠物档案",operType = "查询全部宠物档案",operDesc = "查询全部宠物档案")
    public List<PetDTO> findAll(@RequestBody @Valid PetQuery petQuery) {
        if (petQuery.getName() != null)
            petQuery.setName(petQuery.getName().trim());
        if (petQuery.getUserName() != null)
            petQuery.setUserName(petQuery.getUserName().trim());
        if (petQuery.getSpecies() != null)
            petQuery.setSpecies(petQuery.getSpecies().trim());
        if (petQuery.getVarieties() != null)
            petQuery.setVarieties(petQuery.getVarieties().trim());

        return petService.findPets(petQuery);
    }

    @PostMapping("/findAll/noPage")
    @ApiOperation("查询全部宠物档案-无分页")
    public List<PetDTO> findAllNoPage(@RequestBody @Valid PetQuery petQuery) {
        return petService.findPetsNoPage(petQuery);
    }

    @PostMapping("/add")
    @ApiOperation("添加宠物档案")
    @OperationLogAnnotation(operModul = "宠物档案模块-添加宠物档案",operType = "添加宠物档案",operDesc = "添加宠物档案")
    public String add(@RequestBody @Valid AddPetDto addPetDto) {
        return petService.addPet(addPetDto);
    }

    @GetMapping("/findPet")
    @ApiOperation("通过id查询宠物档案")
    @OperationLogAnnotation(operModul = "宠物档案模块-通过id查询宠物档案",operType = "通过id查询宠物档案",operDesc = "通过id查询宠物档案")
    public AddPetDto findPetById(Long id) {
        return petService.findPetDtoById(id);
    }

    @PostMapping("/updatePet")
    @ApiOperation("更新宠物档案")
    @OperationLogAnnotation(operModul = "宠物档案模块-更新宠物档案",operType = "更新宠物档案",operDesc = "更新宠物档案")
    public String updatePet(@RequestBody @Valid AddPetDto addPetDto) {
        return petService.updatePet(addPetDto);
    }

    @PostMapping("/deletePet")
    @ApiOperation("删除宠物档案")
    @OperationLogAnnotation(operModul = "宠物档案模块-删除宠物档案",operType = "删除宠物档案",operDesc = "删除宠物档案")
    public String deletePet(Long id) {
        if (id == null)
            throw new BusinessException(ErrorCode.PET_ID_NOT_FOUND);
        return petService.deletePet(id);
    }
}
