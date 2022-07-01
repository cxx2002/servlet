package com.cxx.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = this.getServletContext();//与Context类里面拿到的ServletContext是同一个对象
        String username = (String) context.getAttribute("username");//因为程序不知道右边一定是String，所以我们要强制类型转换为String

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        resp.getWriter().println("<h1>名字</h1>="+username);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
