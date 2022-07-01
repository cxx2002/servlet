package com.cxx.filter;

import com.cxx.pojo.User;
import com.cxx.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SysFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //从Session中获取用户
        User user = (User)((HttpServletRequest) servletRequest).getSession().getAttribute(Constants.USER_SESSION);
        if(user==null)//用户被移除或者注销，或者未登录
            ((HttpServletResponse)servletResponse).sendRedirect("../error.jsp");
        else
            filterChain.doFilter(servletRequest,servletResponse);
    }

    public void destroy() {

    }
}
