package com.example.itaem.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private T data;
    private Integer code;
    private String msg;


    public static <T> Result<T> success(T data) {return new Result<T>(data);}

    public static <T> Result<T> error(CodeMsg codeMsg) {return new Result<T>(codeMsg);}


    public Result(T data) {
        this.data = data;
        this.msg = CodeMsg.SUCCESS.getMsg();
        this.code = CodeMsg.SUCCESS.getCode();
    }

    public Result(CodeMsg codeMsg) {
        if (codeMsg != null) {
            this.code = codeMsg.getCode();
            this.msg = codeMsg.getMsg();
        }
    }



}
