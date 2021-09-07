package com.ch.crm.settings.handler;

import com.ch.crm.settings.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyHandler implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("验证过滤器");
        User user = (User) request.getSession().getAttribute("user");
        String path = request.getServletPath();
        System.out.println(path);
        if ("/login.jsp".equals(path)||"/user/loginUser.do".equals(path)){
            System.out.println("验证过滤器1");
            return true;
        }else {
            if (user!=null){
                System.out.println("验证过滤器2");
                return true;
            }else {
                System.out.println("验证过滤器3");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
        return false;
    }
}
