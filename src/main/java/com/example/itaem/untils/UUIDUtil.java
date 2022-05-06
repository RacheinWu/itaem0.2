package com.example.itaem.untils;

import java.util.UUID;

public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


    public static void main(String[] args) {
        String uuid = uuid();
        System.out.println(uuid.substring(0,8).toUpperCase());
    }

}
