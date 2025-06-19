package com.fjp;

import cn.hutool.crypto.digest.MD5;
import com.fjp.mapper.basedata.FormMapper;
import com.fjp.mapper.basedata.UserMapper;
import com.fjp.mapper.pet.CaseRecordMapper;
import com.fjp.mapper.pet.PetMapper;
import com.fjp.pojo.dto.basedata.RoleDTO;
import com.fjp.pojo.dto.basedata.UserDTO;
import com.fjp.pojo.dto.pet.AddPetDto;
import com.fjp.pojo.dto.pet.CaseRecordDTO;
import com.fjp.pojo.dto.pet.PetDTO;
import com.fjp.pojo.entity.basedata.User;
import com.fjp.pojo.entity.pet.CaseRecord;
import com.fjp.pojo.entity.pet.Pet;
import com.fjp.pojo.query.basedata.UserQuery;
import com.fjp.pojo.query.pet.PetQuery;
import com.fjp.service.basedata.ResourceService;
import com.fjp.service.basedata.UserService;
import com.fjp.service.pet.PetService;
import com.fjp.util.BaseContextHolder;
import com.fjp.util.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class PetHospitalApplicationTests {
    @Autowired
    UserService userService;

    @Autowired
    ResourceService resourceService;

    @Autowired
    PetMapper petMapper;

    @Autowired
    PetService petService;

    @Autowired
    CaseRecordMapper caseRecordMapper;

    @Autowired
    private FormMapper formMapper;

    @Test
    void test9() {
        List<CaseRecordDTO> all = caseRecordMapper.findAll(1L);
        for (CaseRecordDTO dto : all ) {
            System.out.println(dto);
        }
    }

    @Test
    void test8() {
        PetQuery petQuery = new PetQuery();
        petQuery.setFindID(3L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(3L);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("客户");
        ArrayList<RoleDTO> roleDTOS = new ArrayList<>();
        roleDTOS.add(roleDTO);
        userDTO.setRoleDTOList(roleDTOS);
        BaseContextHolder.set("user",userDTO);

        AddPetDto build = AddPetDto.builder()
                .name("大黄")
                .height(10.00)
                .inHospital(0)
                .species("狗")
                .varieties("阿拉斯加")
                .weight(20.00)
                .build();
        petService.addPet(build);
    }

    @Test
    void test2() {
        PetQuery petQuery = new PetQuery();
        petQuery.setFindID(3L);
        UserDTO userDTO = new UserDTO();
        userDTO.setId(3L);
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setName("客户");
        ArrayList<RoleDTO> roleDTOS = new ArrayList<>();
        roleDTOS.add(roleDTO);
        userDTO.setRoleDTOList(roleDTOS);
        BaseContextHolder.set("user",userDTO);
        List<PetDTO> pets = petService.findPetsNoPage(petQuery);
        System.out.println(pets);
        System.out.println(pets.size());
    }

    @Test
    void test1() throws ParseException {
        Pet pet = new Pet();
        //pet.setName("狗");
        //pet.setSpecies("狗");
        //pet.setVarieties("金");
        //pet.setInHospital(1);
        //System.out.println(petMapper.selectAll());
//        String time = "2021-07-21";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date parse = simpleDateFormat.parse(time);
//        pet.setAddTime(parse);
//        System.out.println(pet.getAddTime().toString());
//        List<Pet> all = petMapper.findAll();
//        System.out.println(all);
    }

    @Test
    void contextLoads1() {
        User user = new User();
        user.setId(2L);
        user.setName("张三");
        user.setUsername("zhangsan");
        user.setPassword("123456");
        user.setAddTime(new Date());
        user.setUpdateTime(new Date());
        UserDTO userDTO = BeanUtils.copy(user, UserDTO.class);
        System.out.println(userDTO);
    }

    @Test
    void contextLoads2() {
        User user = new User();
        user.setName("张三");
        user.setUsername("zhangsan");
        user.setPassword("123456");
        UserDTO userDTO = BeanUtils.copy(user, UserDTO.class);
        userService.add(userDTO);
    }

    @Test
    void testPetPercentage() {
        System.out.println(formMapper.petPercentage());
        System.out.println(formMapper.appointmentCount());
    }

}
