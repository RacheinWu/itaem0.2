package com.example.itaem.interceptors;

import com.alibaba.druid.util.StringUtils;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.exception.GlobalException;
import com.example.itaem.result.CodeMsg;
import com.example.itaem.service.LoginService;
import com.example.itaem.service.PreparerService;
import com.example.itaem.untils.CookieUtil;
import com.example.itaem.untils.DateUtil;
import com.example.itaem.untils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    LoginService loginService;

    @Autowired
    PreparerService preparerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        PreparerVo loginU;
        String curTime = DateUtil.yyyy_MM_dd_HH_mm();

        //抓取ip地址:
        String ip = IPUtil.getIP(request);
//        log.info("·" + curTime +" [interceptor]: 当前用户ip:" + ip);

        String token = CookieUtil.getCookieValue(request, LoginService.COOKIE_TOKEN_KEY);

        //从redis中读取用户：
        loginU = loginService.getByToken(response, token);

        //检测用户是否null:
        if (loginU == null) {
            log.info("拦截成功！");
            //注销用户的cookie:
            preparerService.quit(request, response);
            throw new GlobalException(CodeMsg.ACCOUNT_WRONG);
        }
        //注入:
        request.setAttribute("interceptor's_success_user", loginU);

        return true;
    }

}
