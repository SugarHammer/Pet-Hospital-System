package com.fjp.service.impl.pet;

import com.fjp.config.annotation.AutoFieldSuffer;
import com.fjp.enums.AutoFieldType;
import com.fjp.enums.ErrorCode;
import com.fjp.exceptions.BusinessException;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.pet.AddPetDto;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import com.fjp.util.GlobalUtils;
import com.github.pagehelper.Page;
import com.fjp.config.annotation.Pagination;
import com.fjp.mapper.basedata.UserMapper;
import com.fjp.mapper.pet.PetMapper;
import com.fjp.pojo.assembler.pet.PetAssembler;
import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.pet.PetQuery;
import com.fjp.service.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @create_time 2021-9-21
 * @describe 宠物档案Service Impl
 */
@Service("petService")
public class PetServiceImpl implements PetService {
    @Autowired
    private PetMapper petMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Pagination
    public List<PetDTO> findPets(PetQuery petQuery) {
        petQuery.setFindID(checkCustomer());

        List<Pet> pets = petMapper.findAll(petQuery);
        Page<Pet> page = (Page<Pet>) pets;
        return GlobalUtils.toDTOPage(page, pet -> {
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",pet.getUserId());
            List<User> users = userMapper.selectByExample(example);
            return PetAssembler.EntityToDTO(pet,users.get(0).getName());
        });
    }

    @Override
    public List<PetDTO> findPetsNoPage(PetQuery petQuery) {
        petQuery.setFindID(checkCustomer());

        List<Pet> pets = petMapper.findAll(petQuery);
        ArrayList<PetDTO> petDTOS = new ArrayList<>();
        for (Pet pet : pets) {
            Example example = new Example(User.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("id",pet.getUserId());
            List<User> users = userMapper.selectByExample(example);
            PetDTO petDTO = PetAssembler.EntityToDTO(pet, users.get(0).getName());
            petDTOS.add(petDTO);
        }
        return petDTOS;
    }

    @Override
    @AutoFieldSuffer(AutoFieldType.ADD)
    @Transactional(rollbackFor = Exception.class)
    public String addPet(AddPetDto addPetDto) {
        if (checkCustomer() != null)
            addPetDto.setUserId(checkCustomer());
        else {
            if (addPetDto.getUserId() == null)
                throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
            else {
                User user = new User();
                user.setId(addPetDto.getUserId());
                User user1 = userMapper.selectByPrimaryKey(user);
                if (user1 == null)
                    throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
            }
        }
        Pet pet = BeanUtils.copy(addPetDto, Pet.class);
        int insert = petMapper.insert(pet);
        if (insert == 1)
            return "添加成功";
        else
            return "添加失败";
    }

    @Override
    public AddPetDto findPetDtoById(Long id) {
        Pet pet = new Pet();
        pet.setId(id);
        Pet pet1 = petMapper.selectByPrimaryKey(pet);
        if (pet1 == null)
            throw new BusinessException(ErrorCode.PET_ID_NOT_FOUND);
        AddPetDto copy = BeanUtils.copy(pet1, AddPetDto.class);
        return copy;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updatePet(AddPetDto addPetDto) {
        if (checkCustomer() != null)
            addPetDto.setUserId(checkCustomer());
        else {
            if (addPetDto.getUserId() == null)
                throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
            else {
                User user = new User();
                user.setId(addPetDto.getUserId());
                User user1 = userMapper.selectByPrimaryKey(user);
                if (user1 == null)
                    throw new BusinessException(ErrorCode.USER_ID_NOT_FOUND);
            }
        }

        Pet copy = BeanUtils.copy(addPetDto, Pet.class);
        int i = petMapper.updateByPrimaryKeySelective(copy);
        if (i!=0)
            return "更新成功";
        else
            return "更新失败";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String deletePet(Long id) {
        System.out.println(id);
        Pet pet = new Pet();
        pet.setId(id);
        int i = petMapper.deleteByPrimaryKey(pet);
        if (i != 1)
            return "删除失败";
        else
            return "删除成功";
    }

    /**
     * 判断是否是客户
     * @return
     */
    private Long checkCustomer() {
        Boolean flag = true;
        UserDTO user = (UserDTO)BaseContextHolder.get("user");
        if (user == null)
            throw new BusinessException(ErrorCode.ROLE_ID_NOT_FOUND);
        List<RoleDTO> roleDTOList = user.getRoleDTOList();
        for (RoleDTO roleDTO : roleDTOList) {
            if (roleDTO.getName().equals("客户")) {
                flag = false;
            }
        }
        if (flag)
            return null;
        else
            return user.getId();
    }
}
