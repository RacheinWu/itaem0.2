package com.example.itaem.config;

import com.example.itaem.VO.PreparerVo;
import com.example.itaem.myAnnotation.CurUser;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //注解 + PreparerVo.class
        return parameter.getParameterType().isAssignableFrom(PreparerVo.class)
                && parameter.hasParameterAnnotation(CurUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //从拦截器中的request中获取user：
        return (PreparerVo)webRequest.getAttribute("interceptor's_success_user", RequestAttributes.SCOPE_REQUEST);

    }
}
