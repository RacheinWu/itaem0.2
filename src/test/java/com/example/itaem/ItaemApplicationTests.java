package com.example.itaem;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.mapper.testm;
import com.example.itaem.redis.EmailKey;
import com.example.itaem.redis.IPCountKey;
import com.example.itaem.redis.RedisService;
import com.example.itaem.redis.UserKey;
import com.example.itaem.service.LoginService;
import com.example.itaem.service.MailService;
import com.example.itaem.service.PreparerService;
import com.example.itaem.untils.MailFormatUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItaemApplicationTests {

    @Autowired
    PreparerMapper preparerMapper;

    @Autowired
    PreparerService preparerService;

    @Autowired
    MailService mailService;

    @Test
    void contextLoads() {
        String content = MailFormatUtil.sendForReminding("欧贻燊", "考核题目的提交！", "请同学安排时间与师兄约定面试时间\n 期待你后面的表现，看好你！");

//        //发送邮箱：
        mailService.sendHtmlMail("labixiaoxin1@stu.gdou.edu.cn", "[ITAEM团队]祝贺你完成了考核题目:", content);

    }

    @Autowired
    RedisService redisService;

    @Test
    //redis
    void redisT() {
        PreparerVo preparerVo = new PreparerVo();
//        preparerVo.setName("无远近");
//        redisService.set(UserKey.token,"?", preparerVo);
        PreparerVo preparerVo1 = redisService.get(UserKey.token, "c3574fcad2274516b01a83ca0a4ef3f2", PreparerVo.class);
        System.out.println(preparerVo1);

    }

    @Test
    //验证邮箱：
    void setMail() {
        PreparerVo preparerVo = new PreparerVo();
        preparerVo.setName("林志鹏");
        preparerVo.setPhonecall("18924566928");
        preparerService.checkMail("202011621420@stu.gdou.edu.cn", preparerVo);

    }

//    @Test
    //设置邮箱：
//    void setMial2() {
//        preparerService.setMail("18924566928", "wuyuanjian@stu.gdou.edu.cn");
//    }


    @Test
    //reids incr
    void t4() {
        redisService.set(IPCountKey.prefixWithE(5),"19.4.4.3", 111);
        Long incr = redisService.incr(IPCountKey.prefixWithE(5), "19.4.4.3");
        System.out.println(incr);
        redisService.incr(IPCountKey.prefixWithE(5), "19.4.4.3");
        Long j = redisService.get(IPCountKey.prefixWithE(5), "19.4.4.3", Long.class);
        System.out.println(j);

    }


    @Test
        //reids incr
    void t5() {
        Long incr = redisService.incr(IPCountKey.prefixWithE(5), "19.4.4.3");
        System.out.println(incr );

    }

    @Test
    void t6() {
        Long incr = redisService.incr(IPCountKey.prefixWithE(5), "19.4.4.3");
        if (incr == 1) {
            redisService.set(IPCountKey.prefixWithE(5),"19.4.4.3", 100);
        }

    }

    @Test
    void t7() {
        String s = redisService.get(EmailKey.activate, "linzhipeng@stu.gdou.edu.cn", String.class);
        System.out.println(s);
    }

    @Test
    void t8() {
        PreparerVo preparerVo = redisService.get(UserKey.token, "e6034dbac89f484dae7def6aa0956b52", PreparerVo.class);
        System.out.println(preparerVo);
    }

    @Autowired
    LoginService loginService;

    @Autowired
    testm x;


    @Test
    void t9() {
//        loginService.login();
    x.xxx();
    }



}
