package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SessionDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Session和cookie的区别:
            ●Cookie是把用户的数据写给用户的浏览器，浏览器保存(可以保存多个)
            ●Session把用户的数据写到用户独占Session中，服务器端保存(保存重 要的信息，减少服务器资源的浪费)
            ●Session对象由服务创建;
        使用场景:
            ●保存一个登录用户的信息;
            ●购物车信息;
            ●在整个网站中经常会使用的数据，我们将它保存在Session中;
        */
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        //得到Session
        HttpSession session = req.getSession();
        /*Session创建的时候做了什么事情
        Cookie cookie = new Cookie("JSESSIONID", "sessionID");
        resp.addCookie(cookie);*/

        //给Session写东西,Session比较强大，可以直接存一个类，一般都不用Context存，容易崩
        session.setAttribute("name",new Person("小明",18));

        //获取Session的ID
        String sessionId = session.getId();

        //判断Session是不是新创建
        if (session.isNew()) {
            resp.getWriter().write("Session创建成功！ID="+sessionId);
        } else {
            resp.getWriter().write("Session已经在服务器中存在了！ID="+sessionId);
            resp.getWriter().write("<br>存在一个键值对为：name----"+session.getAttribute("name").toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
