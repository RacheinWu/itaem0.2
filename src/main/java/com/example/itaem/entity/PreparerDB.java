package com.example.itaem.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@TableName("t_preparer")
@Data
@ToString
public class PreparerDB {

    private String uid;
    private String name;
    private String majorId;
    private String directionId;
    private String stateId;
    @TableId
    private String phonecall;
    private String mail;
    private String passDB;
    private String salt;
    private String powerId;
    private String sexId;
    private String imgPath;
    private String status;
    private Date lastDate;
    private Date registerDate;
    private String tempCode;
}
