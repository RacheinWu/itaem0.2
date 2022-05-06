package com.example.itaem.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.itaem.VO.RegisterVo;
import com.example.itaem.entity.PreparerDB;
import com.example.itaem.exception.GlobalException;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.redis.EmailKey;
import com.example.itaem.redis.RedisService;
import com.example.itaem.result.CodeMsg;
import com.example.itaem.service.MailService;
import com.example.itaem.service.RegisterService;
import com.example.itaem.untils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Slf4j
public class RegisterServiceImpl implements RegisterService {

    private final String VALUE_DEFAULT = "9099";


    @Autowired
    PreparerMapper preparerMapper;

    @Autowired
    MailService mailService;

    @Autowired
    RedisService redisService;

    @Override
    public void sendMail(HttpServletRequest request, HttpServletResponse response, String mail) {

        if (StringUtils.isEmpty(mail)) throw new GlobalException(CodeMsg.BIND_ERROR);

        //查重：
        QueryWrapper<PreparerDB> wrapper = new QueryWrapper();
        wrapper.eq("mail", mail);
        PreparerDB preparerDB = preparerMapper.selectOne(wrapper);
        if (preparerDB != null) throw new GlobalException(CodeMsg.MAIL_EXITED);



        //随机生成激活码：
        String code = UUIDUtil.uuid().substring(0, 4);


        redisService.set(EmailKey.activate, mail, code);


//        QueryWrapper<RegisterMailStatus> wrapper2 = new QueryWrapper();
//        wrapper2.eq("mail", mail);

//        //检查：
//        RegisterMailStatus mailDB = regMapper.selectOne(wrapper2);
//        if (mailDB == null) {
//            mailDB = new RegisterMailStatus();
//            log.info(mail);
//            mailDB.setMail(mail);
//            mailDB.setCode(code);
//            //创建：
//            regMapper.insert(mailDB);
//        } else {
//            //如果已经存在了缓存中:
//            mailDB.setCode(code);
//            //更新database：
//            regMapper.updateById(mailDB);
//        }

        //发送邮件：
        String content = MailFormatUtil.sendForActivation("新", code);
        mailService.sendHtmlMail(mail, "[ITAEM团队] 关于您的邮箱认证:", content);
        log.info(DateUtil.yyyy_MM_dd_HH_mm() + " 发送了认证邮件 to " + mail + "ip:" + IPUtil.getIP(request));

    }






    @Override
    public void Register(HttpServletRequest request, HttpServletResponse response, RegisterVo registerVo) {

        if (registerVo == null) throw new GlobalException(CodeMsg.BIND_NOT_ENOUGH);
        log.info(IPUtil.getIP(request) + "注册信息为:" + registerVo);
        //拆包：
        String code = registerVo.getCode();
        String mail = registerVo.getMail();
        String name = registerVo.getName();
        String password = registerVo.getPassword();
        String uid = registerVo.getUid();
        String phonecall = registerVo.getPhonecall();

        //校验格式：name,mail,uid,phonecall:
//        RexUtil.AllBindCheck()




        /*校验：*/
        if (StringUtils.isEmpty(mail)) throw new GlobalException(CodeMsg.BIND_ERROR);


        String codeDB = redisService.get(EmailKey.activate, mail, String.class);


        //激活码:
        if (!code.equals(codeDB)) throw new GlobalException(CodeMsg.CODE_ERROR);

        //因为可能这个邮箱在那么一瞬间被人先手速注册了: 所以还是要再次检测邮箱是否存在:
        QueryWrapper<PreparerDB> wrapper1 = new QueryWrapper();
        wrapper1.eq("mail", mail);
        PreparerDB preparerDB = preparerMapper.selectOne(wrapper1);
        if (preparerDB != null) throw new GlobalException(CodeMsg.MAIL_EXITED);


        //对密码进行加密:
        //随机生成salt
        if (StringUtils.isEmpty(password)) throw new GlobalException(CodeMsg.BIND_ERROR);
        String salt = UUIDUtil.uuid().substring(0, 8);
        String passDB = MD5Util.inputPassToDbPass(password, salt);

        //对数据库进行存储:
        preparerDB = new PreparerDB();
        if (!StringUtils.isEmpty(name)) preparerDB.setName(name);
        if (!StringUtils.isEmpty(phonecall)) preparerDB.setPhonecall(phonecall);
        if (!StringUtils.isEmpty(uid)) preparerDB.setUid(uid);
        preparerDB.setPassDB(passDB);
        preparerDB.setMail(mail);
        preparerDB.setPowerId("1");
        preparerDB.setSalt(salt);
        preparerDB.setRegisterDate(new Date());

        //未设置的值 默认为9099:
        preparerDB.setSexId(VALUE_DEFAULT);
        preparerDB.setDirectionId(VALUE_DEFAULT);
        preparerDB.setMajorId(VALUE_DEFAULT);
        preparerDB.setStateId("2");
        preparerDB.setImgPath("http://itaem.cn/file/base/baseimg.png");
        preparerMapper.insert(preparerDB);
        log.info("用户" + preparerDB + "于" + DateUtil.yyyy_MM_dd_HH_mm() + "注册了" + IPUtil.getIP(request)+ "ip:" + IPUtil.getIP(request));

    }
}
