package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class CookieDemo01 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        /*中文字符串如何转码和解码*/
        //URLEncoder.encode("哈哈","utf-8");
        //URLDecoder.decode(cookie.getValue(),"utf-8");

        PrintWriter out = resp.getWriter();

        //cookie，服务端从客户端获取
        Cookie[] cookies = req.getCookies();//这里返回数组，说明cookies可能存在多个

        //判断cookie是否存在
        if (cookies != null) {
            out.write("你上一次访问的时间是：");//如果存在
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookie = cookies[i];
                //获取cookie名字
                if (cookie.getName().equals("lastLoginTime")) {
                    //获取cookie的值
                    long lastLoginTime = Long.parseLong(cookie.getValue());
                    out.write(new Date(lastLoginTime).toLocaleString());
                }
            }
        } else {
            out.write("这是你第一次访问本站");
        }

        //服务给客户端一个cookie
        Cookie cookie = new Cookie("lastLoginTime", System.currentTimeMillis() + "");

        //设置cookie的有效期为一天，如果是默认的-1就是退出即过期
        cookie.setMaxAge(24 * 60 * 60);

        //响应给客户端这个cookie
        resp.addCookie(cookie);

        /*cookie:
        - -般会保存在本地的用户目录下appdata;
        -个网站cookie是否存在上限 !聊聊细节问题
            ●一个Cookie只能保存一 个信息;
            ●一个web站点可以给浏览器发送多个cookie, 最多存放20个cookie;
            ●Cookie大小有限制4kb;
            ●300 个cookie浏览器上限
        删除Cookie;
            ●不设置有效期，关闭浏览器，自动失效;
            ●设置有效期时间为0;*/

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
