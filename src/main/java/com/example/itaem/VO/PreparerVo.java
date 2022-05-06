package com.example.itaem.VO;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

@Data
@ToString
public class PreparerVo implements Serializable {

    private String name;
    private String uid;
    private String gender;
    private String phonecall;
    private String mail;
    private String imgPath;
    private String direction;
    private String major;
    private String state;
    private String power;


}
