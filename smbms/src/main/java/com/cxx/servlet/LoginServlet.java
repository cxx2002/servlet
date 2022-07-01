package com.cxx.servlet;

import com.cxx.pojo.User;
import com.cxx.service.user.UserServiceImpl;
import com.cxx.util.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");

        //和数据库中的密码继续对比，调用Service层
        User user = null;
        try {
            user = new UserServiceImpl().login(userCode, userPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(user!=null){
            //若查到了这个用户，则将用户的信息放到Session中
            req.getSession().setAttribute(Constants.USER_SESSION,user);
            //跳转到主页
            resp.sendRedirect(req.getContextPath()+"/jsp/frame.jsp");
        }else {
            //若没有查到这个用户，则转发会登录页面，并且提示用户名或密码错误
            req.setAttribute("error","用户名或密码错误");
            req.getRequestDispatcher("login.jsp").forward(req,resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
