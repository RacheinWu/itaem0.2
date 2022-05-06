package com.example.itaem.controller;

import com.example.itaem.VO.RegisterVo;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.result.Result;
import com.example.itaem.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RegisterController {


    @Autowired
    RegisterService registerService;


    @AccessLimit(seconds = 10, maxcount = 2)
    @GetMapping("/register/checkMail")
    //检测邮箱：
    public Result checkMail(HttpServletRequest request, HttpServletResponse response, @RequestParam("mail") String mail) {
        registerService.sendMail(request, response, mail);
        return Result.success("邮箱可用");
    }


    @PostMapping("/register/do_register")
    public Result register(HttpServletRequest request, HttpServletResponse response, @RequestBody RegisterVo registerVo) {
        registerService.Register(request, response, registerVo);
        return Result.success("注册成功!");
    }


}
