package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        InputStream stream = this.getServletContext().getResourceAsStream("/WEB-INF/classes/db.properties");
        //相对与这个web应用的相对路径用Context的getResourceAsStream(相对与这个web应用的相对路径)方法获取一个输出流
        Properties prop = new Properties();//util包里的Properties类
        prop.load(stream);//加载这个流

        String user = prop.getProperty("username");//获取键为“username”的键值
        String pwd = prop.getProperty("password");

        resp.getWriter().println(user+":"+pwd);//在web前台输出测试
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
