package com.example.itaem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@TableName("t_suggestion")
public class SuggestionDB {

    @TableId(type = IdType.AUTO)
    private String id;
    private String content;
    private String subject;
    private String uid;
    private Date date;
    private String status;

}
