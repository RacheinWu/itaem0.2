package com.example.itaem.redis;

/**
 * redis缓存 前缀：
 */
public interface KeyPrefix {

    /**
     * 缓存周期:
     * @return
     */
    public int expireSeconds();


    /**
     * 获取前缀：
     * @return
     */
    public String getPrefix();

}
