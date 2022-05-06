package com.example.itaem.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.itaem.VO.PreparerEditVo;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.VO.SuggestionVo;
import com.example.itaem.entity.PreparerDB;
import com.example.itaem.entity.SuggestionDB;
import com.example.itaem.exception.GlobalException;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.mapper.SuggestionMapper;
import com.example.itaem.redis.RedisService;
import com.example.itaem.redis.UserKey;
import com.example.itaem.result.CodeMsg;
import com.example.itaem.service.LoginService;
import com.example.itaem.service.MailService;
import com.example.itaem.service.PreparerService;
import com.example.itaem.untils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
@Slf4j
public class PreparerServiceImpl implements PreparerService {

    @Autowired
    SuggestionMapper suggestionMapper;

    @Autowired
    PreparerMapper preparerMapper;

    @Autowired
    MailService mailService;

    @Autowired
    RedisService redisService;

    @Override
    public PreparerVo getMsg(HttpServletResponse response, String phonecall) {
        PreparerVo preparerVo = preparerMapper.getMsg(phonecall);
        return preparerVo;
    }

    @Override
    public void changeMsg(HttpServletRequest request, HttpServletResponse response, PreparerEditVo preparerEditVo, PreparerVo loginU) {

        if (preparerEditVo == null) throw new GlobalException(CodeMsg.EDITION_MSG_NULL);
        log.info(loginU.toString());

        //根据phonecall查找对应的obj
        String mail = preparerEditVo.getMail();
        String uid = preparerEditVo.getUid();
        String newPass = preparerEditVo.getPassword();
        if (StringUtils.isEmpty(mail)) throw new GlobalException(CodeMsg.EDITION_MSG_NULL);
        //校验：
//        String rex = "/[a-z0-9]{4,16}@stu.gdou.edu.cn$/";
//        if (!mail.matches(rex)) throw new GlobalException(CodeMsg.REX_MAIL_ERROR);

        QueryWrapper<PreparerDB> wrapper = new QueryWrapper<>();
        wrapper.eq("mail", mail);
        PreparerDB one = preparerMapper.selectOne(wrapper);
        if (one != null) throw new GlobalException(CodeMsg.MAIL_EXITED);
        if (!RexUtil.REX_UID(uid)) throw new GlobalException(CodeMsg.BIND_ERROR);
        if (!RexUtil.REX_MAIL(mail)) throw new GlobalException(CodeMsg.BIND_ERROR);


        //先从数据库中获取对象:
        PreparerDB preparerDB = preparerMapper.selectById(loginU.getPhonecall());
        if (preparerDB == null) throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
        String salt = preparerDB.getSalt();
        //MD5加密:
        String newPassDB = MD5Util.inputPassToDbPass(newPass, salt);

        //重新设置:
        preparerDB.setMail(mail);
        preparerDB.setPassDB(newPassDB);
        preparerDB.setUid(uid);
//        if (!StringUtils.isEmpty(imgPath)) preparerDB.setImgPath(imgPath);
//        preparerDB.setName();

        //更新数据库：
        preparerMapper.updateById(preparerDB);
        redisService.set(UserKey.token, CookieUtil.getCookieValue(request, LoginService.COOKIE_TOKEN_KEY), preparerDB);

//
//        String content = MailFormatUtil.sendForReminding(loginU.getName(), "信息修改", "");
//        mailService.sendHtmlMail(mail, "[ITAEM团队]您刚刚进行了信息修改操作：", content);
    }







    @Override
    public void checkMail(String mail, PreparerVo preparerVo) {
        if (StringUtils.isEmpty(mail)) throw new GlobalException(CodeMsg.ERROR_SERVER);

        String name = preparerVo.getName();
        String phonecall = preparerVo.getPhonecall();
        log.info("用户:" + name + "试图验证的邮箱是：" + mail);
        //检测邮箱是否存在：
        QueryWrapper<PreparerDB> wrapper = new QueryWrapper();
        wrapper.eq("mail",mail);
        PreparerDB preparerDB = preparerMapper.selectOne(wrapper);
        if (preparerDB != null) throw new GlobalException(CodeMsg.MAIL_EXITED);

        //设置验证码：
        String code = UUIDUtil.uuid().substring(2, 6);
        //将验证码存入database中：
        preparerDB.setStatus(code);
        preparerMapper.updateById(preparerDB);

        String content = MailFormatUtil.sendForActivation(name, code);

        mailService.sendHtmlMail(mail, "[ITAEM团队] 关于您的邮箱认证:", content);
    }




    @Override
    public void setMail(PreparerVo loginU, String mail, String code) {
        if (StringUtils.isEmpty(mail) && StringUtils.isEmpty(code)) throw new GlobalException(CodeMsg.ERROR_SERVER);
        //获取原来的preparerDB：
        String phonecall = loginU.getPhonecall();
        PreparerDB preparerDB = preparerMapper.selectById(phonecall);

        //校验status-code:
        String status = preparerDB.getStatus();
        if (!status.equals(code)) throw new GlobalException(CodeMsg.CODE_ERROR);

        //设置邮箱：
        preparerDB.setMail(mail);
        preparerMapper.updateById(preparerDB);
        log.info("用户:" + preparerDB.getName() +" 验证邮箱通过 目前邮箱:" + mail);
    }







    @Override
    public void suggest(SuggestionVo suggestionVo, PreparerVo loginU) {
        if (loginU == null) throw new GlobalException(CodeMsg.ERROR_SERVER);

        if (suggestionVo == null) throw new GlobalException(CodeMsg.ERROR_SERVER);
        //拆包：
        String suggestContent = suggestionVo.getContent();
        String suggestSubject = suggestionVo.getSubject();
        if (StringUtils.isEmpty(suggestContent) || StringUtils.isEmpty(suggestSubject)) throw new GlobalException(CodeMsg.BIND_NOT_ENOUGH);

        //设置:
        SuggestionDB suggestionDB = new SuggestionDB();
        suggestionDB.setContent(suggestContent);
        suggestionDB.setSubject(suggestSubject);
        suggestionDB.setDate(new Date());
        suggestionDB.setUid(loginU.getUid());
        suggestionDB.setStatus("待处理");

        //更新数据库
        suggestionMapper.insert(suggestionDB);

        //send mail：
        String mailContent = MailFormatUtil.sendForReminding(loginU.getName(), "建议留言", "再次感谢您的留言，谢谢你~");
        mailService.sendHtmlMail(loginU.getMail(), "[ITAEM团队] 您刚刚在itaem.cn上进行了：", mailContent);
        log.info(DateUtil.yyyy_MM_dd_HH_mm() + "对" + loginU.getName() + "发送了一封信");
    }

    @Override
    public void quit(HttpServletRequest request, HttpServletResponse response) {
        //获取cookie:
        Cookie cookie = CookieUtil.getCookie(request, LoginService.COOKIE_TOKEN_KEY);
        //清除cookie
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }


}
