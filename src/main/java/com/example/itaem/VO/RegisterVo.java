package com.example.itaem.VO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RegisterVo {

    private String mail;
    private String name;
    private String uid;
    private String phonecall;
    private String password;
    private String code;//激活码




}
