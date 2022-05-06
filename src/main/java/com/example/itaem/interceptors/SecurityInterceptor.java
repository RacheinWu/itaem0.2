package com.example.itaem.interceptors;

import com.example.itaem.exception.GlobalException;
import com.example.itaem.myAnno.AccessLimit;
import com.example.itaem.redis.IPCountKey;
import com.example.itaem.redis.RedisService;
import com.example.itaem.result.CodeMsg;
import com.example.itaem.untils.IPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author 吴远健
 * @Date 2021/11/2x
 */
@Component
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    private final long PRISONER = 30;


    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法中的注解
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);

            if (accessLimit == null) return true;
//            System.out.println(h);

            int seconds = accessLimit.seconds();
            int maxcount = accessLimit.maxcount();


            //redis:
            String curIP = IPUtil.getIP(request);
            Long incr = redisService.incr(IPCountKey.prefixWithE(seconds), curIP);
            log.info("curIP" + curIP + "5秒内访问了:"  + incr + "次数");
            if (incr == 1) {
                redisService.set(IPCountKey.prefixWithE(seconds), curIP,1);
            }
            else if (incr < maxcount) {
                redisService.incr(IPCountKey.prefixWithE(seconds), curIP);
            }
//            else if (incr == PRISONER) {
//                //
//            }
            else {
                log.info("警告！curIP" + curIP + "频繁访问!");
                throw new GlobalException(CodeMsg.ACCESS_LIMIT_REACHED);
            }
        }


        return true;
    }
}