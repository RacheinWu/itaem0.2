package com.example.itaem.service;

import com.example.itaem.VO.RegisterVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface RegisterService {


    void sendMail(HttpServletRequest request, HttpServletResponse response, String mail);

    void Register(HttpServletRequest request, HttpServletResponse response, RegisterVo registerVo);

}
