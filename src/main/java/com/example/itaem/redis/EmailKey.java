package com.example.itaem.redis;

public class EmailKey extends BasePrefix{

    private static final int EMAIL_EXPIRE = 900;//默认为15min

    public EmailKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static EmailKey activate = new EmailKey(EMAIL_EXPIRE, "mail");


}
