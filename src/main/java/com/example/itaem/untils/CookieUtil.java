package com.example.itaem.untils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author 吴远健
 * @Date 2021/11/2x
 */

public class CookieUtil {


    private static Cookie base(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length <= 0) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(key)) {
                return cookie;
            }
        }
        return null;
    }


    public static Cookie getCookie(HttpServletRequest request, String key) {
        return base(request, key);
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        Cookie cookie = base(request, key);
        if (cookie != null) return cookie.getValue();
        return null;
    }


}
