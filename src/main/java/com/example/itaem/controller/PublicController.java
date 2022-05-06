package com.example.itaem.controller;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.VO.SuggestionVo;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.myAnnotation.CurUser;
import com.example.itaem.result.Result;
import com.example.itaem.service.PreparerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class PublicController {

    @Autowired
    PreparerService preparerService;

    //建议留言：
    @AccessLimit(seconds = 3600, maxcount = 2)
    @PostMapping("public/suggestion")
    public Result suggestion(@RequestBody SuggestionVo suggestionVo, @CurUser PreparerVo loginU) {

        preparerService.suggest(suggestionVo, loginU);
        return Result.success("感谢您的建议！");
    }


    @GetMapping("text/{id}")
    public String s(@PathVariable("id") int id) {

        return "2";
    }


    //

    @GetMapping("/xxx")
    public void x(HttpServletRequest request, @CurUser PreparerVo userVo) {
//        System.out.println(request.getAttribute("r"));
        System.out.println(userVo);
    }

}
