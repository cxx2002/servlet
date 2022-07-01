package com.cxx.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ServletDemo04 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();
        //通过context请求转发，比较少用，一般都是通过request来请求转发
        //重定向的时候要写req.getContextPath()+"/gp",转发的时候不用，直接写/gp,因为/代表了当前web应用路径
        context.getRequestDispatcher("/gp").forward(req,resp);//先建立转发路径为“/gp”,再调用forward请求转发
        // 路径是sd4没有变化，但是走的却是gp，因为把gp的请求给转发到sd4来了
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
