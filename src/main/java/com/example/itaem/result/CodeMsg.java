package com.example.itaem.result;

import io.swagger.annotations.ApiModel;

@ApiModel
public class CodeMsg {

    private int code;

    private String msg;

    //通用：
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg ERROR_SERVER =  new CodeMsg(500101,"服务器端错误");
    public static CodeMsg BIND_ERROR = new CodeMsg(500102, "参数校验异常：%s");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500103, "访问频繁，请稍等！");
    public static CodeMsg TOKEN_EMPTY = new CodeMsg(500104, "token过期失效或者为空！");
    public static CodeMsg ACCOUNT_WRONG = new CodeMsg(500105, "身份验证不通过!");
    public static CodeMsg BIND_NOT_ENOUGH = new CodeMsg(500106, "参数不完整哦！");



    //登陆类：
    public static CodeMsg SESSION_ERROR = new CodeMsg(500200, "Session不存在或者已经失效");
    public static CodeMsg ACCOUNT_EMPTY = new CodeMsg(500201, "登录账号不能空");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500202, "登录密码不能为空");
    public static CodeMsg ACCOUNT_NOT_EXIST = new CodeMsg(500203,"用户不存在！");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500204,"密码错误！");
    public static CodeMsg PASSWORD_FORMAT_ERROR = new CodeMsg(500205, "登录密码长度不符合！");
    public static CodeMsg CODE_ERROR = new CodeMsg(500206, "校验码错误！");
    public static CodeMsg ACCOUNT_FORMAT_ERROR = new CodeMsg(500207, "账号格式错误！");


    //查询类：
    public static CodeMsg MSG_NOT_EXIST = new CodeMsg(50031,"查无数据!");


    //文件类：
    public static CodeMsg FILE_MEMORY_OVER = new CodeMsg(50041,"内存超过限制！");
    public static CodeMsg FILE_EMPTY = new CodeMsg(50042,"不能上传空文件！");
    public static CodeMsg FILE_NOT_EXIT = new CodeMsg(50043,"文件不存在！");


    //权限类： 5005XX
    public static CodeMsg PROHIBIT_ACCESS_ELEMENT = new CodeMsg(500501,"游客没有权限享用或者访问团队资源哦，请先登录~");
    public static CodeMsg PROHIBIT_ACCESS_INNER = new CodeMsg(500502,"您不是ITAEM正式成员,权限不足！");
    public static CodeMsg PROHIBIT_ACCESS_SERVER = new CodeMsg(500503,"服务器管理过于复杂，请联系站长进行操作哦~");

    //黑名单类： 5006XX
    public static CodeMsg BAN_IP = new CodeMsg(500601,"由于你在网络上散发或者做出危害公众安全的行为，现本团队已剔除你的数据和ip！");

    //修改类：  5007XX
    public static CodeMsg EDITION_MSG_NULL = new CodeMsg(500701, "修改信息为空！");
    public static CodeMsg EDITION_MSG_NOT_ENOUGH = new CodeMsg(500702, "信息传输少项！");
    public static CodeMsg REX_MAIL_ERROR = new CodeMsg(500703, "邮箱格式错误！");
    public static CodeMsg MAIL_EXITED = new CodeMsg(500704, "邮箱已经存在了！");

    //注册类: 5008XX
//    public static CodeMsg MAIL_EXITED = new CodeMsg(500801, "！");


    public CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 返回带参数的错误码
     * @param args
     * @return
     */
    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
