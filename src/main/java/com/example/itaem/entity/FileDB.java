package com.example.itaem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@TableName("files")
@ToString
public class FileDB {

    @TableId(type = IdType.AUTO)
    private String id;
    private String originalFileName;
    private String newFileName;
    private String ext;
    private Long size;
    private String type;
    private Date uploadTime;
    private String uid;
    private String path;



}
