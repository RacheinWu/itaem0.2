package com.example.itaem.controller;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.entity.FileDB;
import com.example.itaem.mapper.FileMapper;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.myAnnotation.CurUser;
import com.example.itaem.result.Result;
import com.example.itaem.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class PreparerFileController {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    FileService fileService;

    @Autowired
    PreparerMapper preparerMapper;


    //上传
    @AccessLimit(seconds = 5, maxcount = 2)
    @PostMapping("preparer/upload")
    public Result upload(HttpServletRequest request, HttpServletResponse response, List<MultipartFile> files, @CurUser PreparerVo curUser) throws IOException {
        fileService.upload(request, response, files, curUser);
        return Result.success("上传成功");
    }

    //showAll
    @GetMapping("preparer/showAll")
    public Result showAll(HttpServletRequest request, HttpServletResponse response, @CurUser PreparerVo preparerVo) {

        List<FileDB> fileDBS = fileService.showAll(request, response, preparerVo);
        return Result.success(fileDBS);
    }


    //下载
    @GetMapping("preparer/download")
    @AccessLimit(seconds = 5, maxcount = 5)
    public Result download(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, @CurUser PreparerVo loginU) {
        FileDB fileDB = fileMapper.selectById(id);
        return Result.success("http://itaem.cn" + "/" + "file" + fileDB.getPath() + "/" + fileDB.getNewFileName());
//        fileService.download(request, response, id, loginU);
//        return Result.success("开始下载！");
    }


    //删除：
    @GetMapping("preparer/delete")
    public Result delete(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") String id, @CurUser PreparerVo curUser) {
        fileService.delete(request, response, id, curUser);
        return Result.success("删除成功!");
    }




}
