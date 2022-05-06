package com.example.itaem.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.itaem.VO.PreparerVo;
import com.example.itaem.entity.FileDB;
import com.example.itaem.entity.PreparerDB;
import com.example.itaem.exception.GlobalException;
import com.example.itaem.mapper.FileMapper;
import com.example.itaem.mapper.PreparerMapper;
import com.example.itaem.redis.RedisService;
import com.example.itaem.redis.UserKey;
import com.example.itaem.result.CodeMsg;
import com.example.itaem.service.FileService;
import com.example.itaem.service.LoginService;
import com.example.itaem.service.MailService;
import com.example.itaem.untils.CookieUtil;
import com.example.itaem.untils.MailFormatUtil;
import com.example.itaem.untils.UUIDUtil;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    FileMapper fileMapper;

    @Autowired
    MailService mailService;

    @Autowired
    PreparerMapper preparerMapper;

    @Autowired
    RedisService redisService;

    @Value("${DBpath}")
    String DBpath;

    @Override
    public void upload(HttpServletRequest request, HttpServletResponse response, List<MultipartFile> files, PreparerVo curUser) {
        log.info("upload : " + new SimpleDateFormat("yyyyMMdd HH:mm:ss").format(new Date()));
        //获取当前登录对象:
        /*

         */
        for (MultipartFile file : files) {

            //检测空文件
            if (file == null || file.getContentType().equals("application/octer-stream")) throw new GlobalException(CodeMsg.FILE_EMPTY);

            //检测大小:
            long size = file.getSize();
            if (size > 500E6) throw new GlobalException(CodeMsg.FILE_MEMORY_OVER);
            log.info("file's size = " + size);

            //originalName:
            String originalFilename = file.getOriginalFilename();
            log.info("file's originalName = " + originalFilename);

            //extension:
            String extension = "." + FilenameUtils.getExtension(originalFilename);
            log.info("file's extension = " + extension);

            //newFileName:
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +
                    UUIDUtil.uuid().substring(6) + extension;
            log.info("file's newName = " + newFileName);

            //type:
            String type = file.getContentType();
            log.info("file's type: " + type);

            //DATE：
            /*

             */
            //根据学号进行上传文件:
            String formPath = "/" + "houtai2lun" + "/" + curUser.getUid();
            String dataDirPath = DBpath + formPath;
            log.info("file address: " + dataDirPath);
            File dataDir = new File(dataDirPath);//document
            if (!dataDir.exists()) dataDir.mkdirs();

            //upload:
            try {
                file.transferTo(new File(dataDir,newFileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //put it into database:

            PreparerDB preparerDB = preparerMapper.selectById(curUser.getPhonecall());
            preparerDB.setStateId("3");
            preparerMapper.updateById(preparerDB);

            //redis 更新用户信息:
            curUser.setState("已提交");
            redisService.set(UserKey.token, CookieUtil.getCookieValue(request, LoginService.COOKIE_TOKEN_KEY), curUser);


            FileDB fileDB = new FileDB();
            fileDB.setExt(extension);
            fileDB.setNewFileName(newFileName);
            fileDB.setOriginalFileName(originalFilename);
            fileDB.setPath(formPath);
            fileDB.setUid(curUser.getUid());
            fileDB.setSize(size);
            fileDB.setType(type);
            fileDB.setUploadTime(new Date());

            fileMapper.insert(fileDB);

        }

        String content = MailFormatUtil.sendForReminding(curUser.getName(), "考核题目的提交！", "请同学安排时间与师兄约定面试时间\n 期待你后面的表现，看好你！");

//        //发送邮箱：
        mailService.sendHtmlMail(curUser.getMail(), "[ITAEM团队]祝贺你完成了考核题目:", content);
    }

    @Override
    public List<FileDB> showAll(HttpServletRequest request, HttpServletResponse response, PreparerVo curUser) {

        if (curUser == null) throw new GlobalException(CodeMsg.ACCOUNT_WRONG);
        PreparerDB preparerDB = preparerMapper.selectById(curUser.getPhonecall());

        QueryWrapper<FileDB> wrapper = new QueryWrapper<FileDB>().eq("uid", preparerDB.getUid());
        List<FileDB> fileDBS = fileMapper.selectList(wrapper);

//        m

        if (fileDBS == null) return null;

        return fileDBS;
    }


    @Override
    public void delete(HttpServletRequest request, HttpServletResponse response, String id, PreparerVo curUser) {
        FileDB fileDB = fileMapper.selectById(id);

        String uid = curUser.getUid();
        if (!uid.equals(fileDB.getUid())) throw new GlobalException(CodeMsg.ERROR_SERVER);


        log.info(fileDB.toString());

        //获取路径:
        String realPath = DBpath;
        String formPath = fileDB.getPath();
        String dataDirPath = realPath + formPath;
        File file = new File(dataDirPath, fileDB.getNewFileName());
        if (file.exists()){
            file.delete();//立即删除
        }
        log.info(curUser.getName() + "用户" + "删除了" + fileDB.getNewFileName());


        QueryWrapper<PreparerDB> wrapper = new QueryWrapper();
        wrapper.eq("uid", uid);
        PreparerDB preparerDB = preparerMapper.selectOne(wrapper);

        QueryWrapper<FileDB> queryWrapper = new QueryWrapper();
        queryWrapper.eq("uid", uid);
        List<FileDB> fileDBS = fileMapper.selectList(queryWrapper);


        if (fileDBS.size() == 0) preparerDB.setStateId("2");
        preparerMapper.updateById(preparerDB);

        //删除form database：
        fileMapper.deleteById(id);

    }

    @Override
    public void download(HttpServletRequest request, HttpServletResponse response, String id, PreparerVo curUser){
        try {
            //获取文件信息:
            if (StringUtils.isEmpty(id)) throw new GlobalException(CodeMsg.FILE_NOT_EXIT);
            if (curUser == null) throw new GlobalException(CodeMsg.ERROR_SERVER);
            FileDB file = fileMapper.selectById(id);
            log.info(curUser.getUid() + "用户" + "下载了" + file.getOriginalFileName() + "文件");
//        判断用户是在线打开还是下载
            //更新下载次数:
            //根据文件信息中文件的名字和文件存储的路径获取文件输入流:
            String realPath = DBpath + file.getPath();
            //获取文件输入流:
            FileInputStream is = new FileInputStream(new File(realPath, file.getNewFileName()));
            //附件下载:
            response.setHeader("content-disposition","attachment;fileName=" + URLEncoder.encode(file.getOriginalFileName(),"UTF-8"));
            //获取响应输出流
            ServletOutputStream os = response.getOutputStream();
            //文件拷贝
            IOUtils.copy(is,os);
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        } catch (IOException e) {
            log.info("ERROR! 文件下载出错！");
            e.printStackTrace();
        }


    }
}
