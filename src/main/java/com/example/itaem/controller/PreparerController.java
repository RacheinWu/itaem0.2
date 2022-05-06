package com.example.itaem.controller;

import com.example.itaem.VO.PreparerEditVo;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.myAnnotation.CurUser;
import com.example.itaem.result.Result;
import com.example.itaem.service.PreparerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Slf4j
public class PreparerController {

    @Autowired
    PreparerService preparerService;

    //修改信息：

    @AccessLimit(seconds = 15, maxcount = 2)
    @PostMapping("preparer/editMsg")
    public Result changeMsg(HttpServletRequest request, HttpServletResponse response, @RequestBody PreparerEditVo preparerEditVo, @CurUser PreparerVo preparerVo) {
        preparerService.changeMsg(request, response, preparerEditVo, preparerVo);
        return Result.success("修改成功!");
    }


    @AccessLimit(seconds = 15, maxcount = 2)
    //邮箱验证(发送验证):
    @GetMapping("preparer/checkMail")
    public Result checkMail(HttpServletRequest request, HttpServletResponse response, @RequestParam("mail") String mail, @CurUser PreparerVo preparerVo) {
        preparerService.checkMail(mail, preparerVo);
        return Result.success("已发送验证邮件！");
    }

    //注销用户：
    @GetMapping("preparer/quit")
    public Result quit(HttpServletRequest request, HttpServletResponse response) {
        preparerService.quit(request, response);
        return Result.success("注销成功!");
    }



}
