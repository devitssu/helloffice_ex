package com.kh.helloffice;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        if(handler instanceof HandlerMethod == false){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Level level = handlerMethod.getBean().getClass().getAnnotation(Level.class);
        if(level == null) return true;
        if(level.adminLevel().equals(AdminLevel.USER)) return true;

        String adminLevel = (String)request.getSession().getAttribute("adminLevel");
        if(level.adminLevel().equals(AdminLevel.ADMIN)){
            if(adminLevel.equals(AdminLevel.ADMIN.name())){
                return true;
            }
        }

        throw new NoAccessException("접근 권한이 없습니다.");
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
