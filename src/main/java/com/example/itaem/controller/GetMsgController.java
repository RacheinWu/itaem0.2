package com.example.itaem.controller;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.myAnnotation.CurUser;
import com.example.itaem.result.Result;
import com.example.itaem.service.PreparerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class GetMsgController {

    @Autowired
    PreparerService preparerService;

    @GetMapping("getMsg")
    public Result getMsg(HttpServletRequest request, HttpServletResponse response, @CurUser PreparerVo preparerVo) {
        return Result.success(preparerVo);
    }

}
