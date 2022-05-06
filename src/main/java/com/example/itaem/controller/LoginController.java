package com.example.itaem.controller;

import com.example.itaem.VO.LoginVo;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.result.Result;
import com.example.itaem.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
public class LoginController {

    @Autowired
    LoginService loginService;


//    @GetMapping("login/sendCode")
//    public Result sendCode(HttpServletRequest request, HttpServletResponse response, @RequestParam String account, @RequestParam Integer id) {
//        loginService.beforeLogin(request, response, account, id);
//        return Result.success(new Date());//返回时间，让前端进行计时，防止恶意发送邮件；
//    }

    @AccessLimit(seconds = 5, maxcount = 3)
    @PostMapping("login/do_login")
    public Result do_login(HttpServletResponse response, HttpServletRequest request, @RequestBody LoginVo loginVo) {
        String token = loginService.login(response, request, loginVo);
        return Result.success("欢迎回来~");
    }



}
