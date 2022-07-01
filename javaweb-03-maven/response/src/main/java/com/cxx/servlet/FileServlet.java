package com.cxx.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.要获取下载文件的路径
        //这个的路径会是哈哈.png在本地的路径，不是target里面的路径
        //String realPath = this.getServletContext().getRealPath("/哈哈.png");
        String realPath = "C:\\Users\\陈喜喜\\IdeaProjects\\javaweb-03-maven\\response\\target\\classes\\哈哈.png";
        System.out.println("下载文件的路径为：" + realPath);
        //2.下载的文件名是什么（★★★realPath.substring(realPath.lastIndexOf("\\") + 1)小技巧）
        String fileName = realPath.substring(realPath.lastIndexOf("\\") + 1);//截取realPath的最后的文件名
        //3.设置想办法让浏览器能够支持下载我们需要的东西
        resp.setHeader("Content-Disposition", "attachment;filename=" +
                URLEncoder.encode(fileName, "utf-8"));//中文文件名URLEncoder.encode编码，否则中文会乱码
        //4.获取下载文件的输入流
        FileInputStream in = new FileInputStream(realPath);
        //5.创建缓冲区
        int len = 0;
        byte[] buffer = new byte[1024];
        //6.获取OutputStream对象
        ServletOutputStream out = resp.getOutputStream();
        //7.奖FileOutputStream流写入到buffer缓冲区,使用OutputStream奖缓冲区中的数据输出到客户端
        while ((len = in.read(buffer)) > 0)
            out.write(buffer, 0, len);
        //8.关闭流
        in.close();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
