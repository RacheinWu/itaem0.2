package com.example.itaem.controller;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.myAnnotation.CurUser;
import com.example.itaem.result.Result;
import com.example.itaem.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class GetExamController {

    @Autowired
    FileService fileService;

    @GetMapping("preparer/getExam")
    public Result getExam(HttpServletRequest request, HttpServletResponse response, @CurUser PreparerVo preparerVo) {
        fileService.download(request, response,"123", preparerVo);
        return Result.success("下载成功！");
    }


}
