package com.cxx.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Context extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //this.getInitParameter()   初始化参数（用的少）
        //this.getServletConfig()   Servlet配置

        //this.getServletContext()  Servlet上下文
        /*web容器在启动的时候，它会为每一个web程序都创建一个对应的Context对象，它代表了当前的web应用
        Context凌驾于所有Servlet之上，可以保存一些东西，达到各个Servlet可以进行数据交互*/
        ServletContext context = this.getServletContext();
        String username = "cxx";
        context.setAttribute("username",username);

        System.out.println("hello!!!!");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
