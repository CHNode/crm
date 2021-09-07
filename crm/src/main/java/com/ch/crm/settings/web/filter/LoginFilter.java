package com.ch.crm.settings.web.filter;

import com.ch.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("验证过滤器");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String path = request.getServletPath();
        System.out.println(path);

        if ("/login.jsp".equals(path)||"/user/loginUser.do".equals(path)){
            System.out.println("验证过滤器1");
            filterChain.doFilter(request,response);
        }else {
            if (user!=null){
                System.out.println("验证过滤器2");
                filterChain.doFilter(request,response);
            }else {
                System.out.println("验证过滤器3");
                response.sendRedirect(request.getContextPath()+"/login.jsp");
            }
        }
    }

    @Override
    public void destroy() {

    }
}
