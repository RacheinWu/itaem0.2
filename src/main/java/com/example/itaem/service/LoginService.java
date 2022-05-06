package com.example.itaem.service;

import com.example.itaem.VO.LoginVo;
import com.example.itaem.VO.PreparerVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface LoginService {

    static final String COOKIE_TOKEN_KEY = "token";


    /**
     * 登录
     * @param response
     * @param loginVo
     * @return
     */
    String login(HttpServletResponse response, HttpServletRequest request, LoginVo loginVo);

    /**
     * 添加cushion:
     */
    void addCookie(HttpServletResponse response, String TOKEN, PreparerVo preparerVo);


    /**
     * 根据cookie的火星文获取 PreparerVo
     */
    PreparerVo getByToken(HttpServletResponse response, String token);


}
