package com.example.itaem.service;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.entity.FileDB;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FileService {

    void upload(HttpServletRequest request, HttpServletResponse response, List<MultipartFile> files, PreparerVo curUser);

    List<FileDB> showAll(HttpServletRequest request, HttpServletResponse response, PreparerVo curUser);

    void delete(HttpServletRequest request, HttpServletResponse response, String id, PreparerVo curUser);

    void download(HttpServletRequest request, HttpServletResponse response, String id, PreparerVo curUser);


}
