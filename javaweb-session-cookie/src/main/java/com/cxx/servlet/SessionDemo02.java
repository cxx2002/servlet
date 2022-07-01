package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionDemo02 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        //得到Session
        HttpSession session = req.getSession();

        //手动注销Session,每个浏览器就只有一个Session，一打开就会有,注销后再下一次打开浏览器又会有一个新的Session
        session.removeAttribute("name");
        session.invalidate();

        /*自动失效  在web.xml配置  Session自动失效后，浏览器会马上换一个新的Session
        *  <!--配置Session的默认失效时间-->
        <session-config>
            <!--Session在15分钟后自动失效-->
            <session-timeout>15</session-timeout>
        </session-config>
        * */

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       doGet(req, resp);
    }
}
