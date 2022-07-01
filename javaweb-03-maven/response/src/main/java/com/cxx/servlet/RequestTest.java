package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RequestTest extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理请求
        String username = req.getParameter("username");//获取参数
        String password = req.getParameter("password");

        System.out.println(username+":"+password);

        //重定向的时候，一定要注意路径，否则404
        resp.sendRedirect(req.getContextPath()+"/success.jsp");
        //重定向的时候要写req.getContextPath()+"/success.jsp",转发的时候不用，直接写/success.jsp,因为/代表了当前web应用路径

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
