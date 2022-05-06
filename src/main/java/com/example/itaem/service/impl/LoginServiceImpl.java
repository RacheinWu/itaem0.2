package com.example.itaem.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.itaem.VO.LoginVo;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.entity.PreparerDB;
import com.example.itaem.exception.GlobalException;
import com.example.itaem.mapper.PreparerMapper;
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
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {

    @Autowired
    PreparerMapper preparerMapper;

    @Autowired
    RedisService redisService;

    @Autowired
    PreparerService preparerService;

    @Autowired
    MailService mailService;


//    @Override
//    public void beforeLogin(HttpServletRequest request, HttpServletResponse response, String account, Integer id) {
//        PreparerDB preparerDB;
//
//        //进行account校验
//        if (StringUtils.isEmpty(account)) throw new GlobalException(CodeMsg.ACCOUNT_EMPTY);
//        if (id == null) throw new GlobalException(CodeMsg.BIND_ERROR);
//
//        QueryWrapper<PreparerDB> wrapper = new QueryWrapper();
//        switch (id) {
//            case 1 : {
//                //学号登录:
//                wrapper.eq("uid", account);
//                break;
//            }
//            case 2: {
//                //邮箱登录:
//                wrapper.eq("mail", account);
//                break;
//            }
//            case 3: {
//                //手机号登录:
//                wrapper.eq("phonecall", account);
//                break;
//            }
//            default: {
//                throw new GlobalException(CodeMsg.BIND_ERROR);
//            }
//        }
//
//        //先验证是否有这个account:
//        preparerDB = preparerMapper.selectOne(wrapper);
//        if (preparerDB == null) throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
//
//        //随机生成code:
//        String code = UUIDUtil.uuid().substring(2, 6);
//        preparerDB.setTempCode(code);
//
//        //update：
//        preparerMapper.updateById(preparerDB);
//
//        //获取基本信息
//        String name = preparerDB.getName();
//
//        //发邮件：
//        String content = MailFormatUtil.sendWithCodeForLogin(name, code);
//        mailService.sendHtmlMail(name, "[ITAEM团队] 关于你的登录验证:", content);
//
//    }

    @Override
    public String login(HttpServletResponse response, HttpServletRequest request, LoginVo loginVo) {
        log.info(IPUtil.getIP(request) + loginVo);
        if (loginVo == null) throw new GlobalException(CodeMsg.ERROR_SERVER);

        //拆包：
        PreparerDB preparerDB = new PreparerDB();
        String account = loginVo.getAccount();
        String password = loginVo.getPassword();

        //校验1：
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) throw new GlobalException(CodeMsg.BIND_ERROR);

        //database条件创建;
        QueryWrapper<PreparerDB> queryWrapper  = new QueryWrapper();


        //REX:(三选一登录)
        String method = RexUtil.WhichMethodToLogin(account);
        if (StringUtils.isEmpty(method)) throw new GlobalException(CodeMsg.ACCOUNT_FORMAT_ERROR);
        queryWrapper.eq(method, account);


        //校验账号是否存在
        preparerDB = preparerMapper.selectOne(queryWrapper);
        if (preparerDB == null) throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);

        //校验密码：
        String salt = preparerDB.getSalt();
        String passDB = preparerDB.getPassDB();
        String formPass = MD5Util.inputPassToDbPass(password, salt);
        if (!formPass.equals(passDB)) throw new GlobalException(CodeMsg.PASSWORD_ERROR);

        /*success:*/
//        //更新状态code:
//        preparerDB.setStatus(code);
        //更新登陆时间:
        Date date = new Date();
        preparerDB.setLastDate(date);
        //更新database：
        preparerMapper.updateById(preparerDB);

        log.info(preparerDB.getName() + "-> 在" + DateUtil.yyyy_MM_dd_HH_mm() + " 登录了 -ip:" + IPUtil.getIP(request));


        //添加cookie
        String token = UUIDUtil.uuid();
        String phonecall = preparerDB.getPhonecall();


        PreparerVo curUser = preparerService.getMsg(response, phonecall);

        addCookie(response, token, curUser);
        response.setHeader("Access-Control-Allow-Credentials","true");
//        response.setHeader("Access-Control-Allow-Origin","112.74.73.147, 192.168.31.43, 192.168.31.60");
        response.setHeader("Access-Control-Allow-Origin","http://192.168.31.60");
        return token;
    }



    @Override
    //添加cookie:
    public void addCookie(HttpServletResponse response, String token, PreparerVo preparerVo) {
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(CodeMsg.TOKEN_EMPTY);
        }
        redisService.set(UserKey.token,token, preparerVo);
        Cookie cookie = new Cookie(COOKIE_TOKEN_KEY,token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        redisService.get(UserKey.token, token, PreparerVo.class);
        response.addCookie(cookie);
    }


    @Override
    public PreparerVo getByToken(HttpServletResponse response, String token) {
        log.info("token = " + token);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        PreparerVo user = redisService.get(UserKey.token, token, PreparerVo.class);
        log.info("curUser = " + user);
        //延长有效期，有效期等于最后一次操作+有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }


}
