package com.example.itaem.untils;

import com.alibaba.druid.util.StringUtils;

/**
 * 用于正则表达式校验：
 * //在后序开发中，可以使用自定义注解
 */
public class RexUtil {

    //REX:
    private static final String rex_phone = "1\\d{10}";
    private static final String rex_mail = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@stu.gdou.edu.cn$";
    private static final String rex_uid = "2021\\d{8}";


    //手机号验证：
    public static boolean REX_PHONECALL(String phonecall) {
        if (!StringUtils.isEmpty(phonecall) && phonecall.matches(rex_phone))  return true;
        return false;
    }

    //邮箱验证:
    public static boolean REX_MAIL(String mail) {
        if (!StringUtils.isEmpty(mail) && mail.matches(rex_mail)) return true;
        return false;
    }

    //学号验证:
    public static boolean REX_UID(String uid) {
        if (!StringUtils.isEmpty(uid) && uid.matches(rex_uid)) return true;
        return false;
    }



    /**
     * 用于登录时候三选一校验：
     * @param account
     * @return null : method
     */
    public static String WhichMethodToLogin(String account) {
        if (REX_MAIL(account)) return "mail";
        else if (REX_PHONECALL(account)) return "phonecall";
        else if (REX_UID(account)) return "uid";
        else return null;
    }


    public static boolean AllBindCheck(String name, String uid, String phonecall, String mail) {
        if (!uid.matches(rex_uid) || !phonecall.matches(rex_phone) || !mail.matches(rex_mail)) return false;
        return true;
    }

}
