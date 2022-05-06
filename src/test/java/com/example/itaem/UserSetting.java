package com.example.itaem;

import com.example.itaem.entity.PreparerDB;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.untils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class UserSetting {


    @Autowired
    PreparerMapper preparerMapper;

    @Test
    void modifyPass(){
        //手机号码后六位：
        List<PreparerDB> ps = preparerMapper.selectList(null);
        for (PreparerDB p : ps) {
//            System.out.println(p);
            String salt = p.getSalt();
            String phonecall = p.getPhonecall();
            String substring = phonecall.substring(5, 11);
            String passDB = MD5Util.inputPassToDbPass(substring, salt);
            p.setPassDB(passDB);
            preparerMapper.updateById(p);
        }

    }
    @Test
    void testPhone6() {
        String substring = "18924566928".substring(5, 11);
        System.out.println(substring);
    }


    @Test
    void setGender() {
        List<PreparerDB> ps = preparerMapper.selectList(null);
        for (PreparerDB p : ps) {
            if (p.getSexId() == null) {
//                System.out.println();
                p.setSexId("9");
                preparerMapper.updateById(p);
            }
        }

    }

}
