package com.example.itaem.redis;

public class IPCountKey extends BasePrefix{

    public IPCountKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 需要缓存的字段：
     */
    public static IPCountKey prefixWithE(int IP_COUNT_EXPIRE){return new IPCountKey(IP_COUNT_EXPIRE,"ip");}


}
