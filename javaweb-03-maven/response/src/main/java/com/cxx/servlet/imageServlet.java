package com.cxx.servlet;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class imageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //如何让浏览器3秒刷新一次
        resp.setHeader("refresh","3");
        //在内存中创建一个图片
        BufferedImage image = new BufferedImage(800, 220, BufferedImage.TYPE_INT_RGB);
        //得到图片
        Graphics2D g = (Graphics2D) image.getGraphics();//画笔
        //设置图片的背景颜色
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 220);
        //给图片写数据
        g.setColor(Color.red);
        g.setFont(new Font(null, Font.ITALIC, 200));
        g.drawString(getRandom(), 0, 200);
        //告诉浏览器，这个请求用图片方式打开
        resp.setContentType("image/jpeg");
        //网站存在缓存，让浏览器不缓存
        resp.setDateHeader("expires", -1);
        resp.setHeader("cache-control", "no-cache");
        resp.setHeader("Pragma", "co-cache");
        //把图片写给浏览器
        ImageIO.write(image, "jpg", resp.getOutputStream());
    }

    private String getRandom() {
        Random random = new Random();
        String num = random.nextInt(9999999) + "";//0~9999999的随机数
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 7 - num.length(); i++)//七位数的验证码，如果没有七位，在后面补0
            sb.append("0");
        num = num + sb.toString();
        return num;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
