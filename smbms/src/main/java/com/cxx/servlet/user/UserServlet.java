package com.cxx.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.cxx.pojo.Role;
import com.cxx.pojo.User;
import com.cxx.service.role.RoleServiceImpl;
import com.cxx.service.user.UserService;
import com.cxx.service.user.UserServiceImpl;
import com.cxx.util.Constants;
import com.cxx.util.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入UserServlet");
        req.setCharacterEncoding("utf-8");
        //实现servlet复用
        String method = req.getParameter("method");
        if (method.equals("savepwd")){
            this.updatePwd(req,resp);
        }else if (method.equals("pwdmodify")){
            this.pwdModify(req,resp);
        }else if (method.equals("query")){
            this.query(req,resp);
        }else if (method.equals("add")){
            this.add(req,resp);
        }else if (method.equals("modifyexe")){
            this.modify(req,resp);
        }else if (method.equals("deluser")){
            this.delUser(req,resp);
        }else if (method.equals("ucexist")){
            this.userCodeExist(req, resp);
        }else if (method.equals("getrolelist")){
            this.getRoleList(req, resp);
        }else if (method.equals("view")){
            this.getUserById(req,resp,"userview.jsp");
        }else if (method.equals("modify")){
            this.getUserById(req,resp,"usermodify.jsp");
        }
    }

    private void getUserById(HttpServletRequest req, HttpServletResponse resp, String url) throws ServletException, IOException {
        String id = req.getParameter("uid");
        if (!StringUtils.isNullOrEmpty(id)) {
            //调用后台方法得到user对象
            UserService userService = new UserServiceImpl();
            User user = userService.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher(url).forward(req, resp);
        }
    }

    private void getRoleList(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void userCodeExist(HttpServletRequest req, HttpServletResponse resp) {
    }

    private void delUser(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("uid");
        Integer delId = 0;
        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (delId <= 0) {
            resultMap.put("delResult", "notexist");
        } else {
            UserService userService = new UserServiceImpl();
            if (userService.del(delId)) {
                resultMap.put("delResult", "true");
            } else {
                resultMap.put("delResult", "false");
            }
        }

        //把resultMap转换成json对象输出.
        resp.setContentType("application/json");
        PrintWriter outPrintWriter = resp.getWriter();
        outPrintWriter.write(JSONArray.toJSONString(resultMap));
        outPrintWriter.flush();
        outPrintWriter.close();
    }

    //修改用户信息
    private void modify(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String id = req.getParameter("uid");
        String userName = req.getParameter("userName");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        User user = new User();
        user.setId(Integer.valueOf(id));
        user.setUserName(userName);
        user.setGender(Integer.valueOf(gender));
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setPhone(phone);
        user.setAddress(address);
        user.setUserRole(Integer.valueOf(userRole));
        user.setModifyBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());

        UserService userService = new UserServiceImpl();
        if (userService.modify(user)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("usermodify.jsp").forward(req, resp);
        }

    }

    //增加用户信息
    private void add(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String userCode = req.getParameter("userCode");
        String userName = req.getParameter("userName");
        String userPassword = req.getParameter("userPassword");
        String gender = req.getParameter("gender");
        String birthday = req.getParameter("birthday");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String userRole = req.getParameter("userRole");

        User user = new User();
        user.setUserCode(userCode);
        user.setUserName(userName);
        user.setUserPassword(userPassword);
        user.setAddress(address);
        try {
            user.setBirthday(new SimpleDateFormat("yyyy-MM-dd").parse(birthday));//把字符串变成日期。
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) req.getSession().getAttribute(Constants.USER_SESSION)).getId());

        UserService userService = new UserServiceImpl();
        if (userService.add(user)) {
            resp.sendRedirect(req.getContextPath() + "/jsp/user.do?method=query");
        } else {
            req.getRequestDispatcher("useradd.jsp").forward(req, resp);
        }
    }

    //查询用户列表（重点，难点）。
    public  void query(HttpServletRequest req, HttpServletResponse resp){

        //从前端获取数据：
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");
        String pageIndex = req.getParameter("pageIndex");
        int queryUserRole = 0;

        //获取用户列表
        UserServiceImpl userService = new UserServiceImpl();
        List<User> userList = null;

        //第一次走这个请求，一定是第一页，页面大小固定的：
        int pageSize = 5;//可以把这个配置到配置文件中，方便后期修改；
        int currentPageNo = 1;

        if (queryUserName==null){
            queryUserName = "";
        }
        if (temp!=null && !temp.equals("")){
            queryUserRole = Integer.parseInt(temp); //给查询赋值！0，1，2，3
        }
        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总量（分页：上一页，下一页的情况）
        int totalCount = userService.getUserCount(queryUserName,queryUserRole);

        //总页数支持。
        PageSupport pageSupport = new PageSupport();
        pageSupport.setCurrentPageNo(currentPageNo);
        pageSupport.setPageSize(pageSize);
        pageSupport.setTotalCount(totalCount);
        int totalPageCount = pageSupport.getTotalPageCount();//总共有几页。

        //控制首页和尾页。
        //如果页面小于1，就显示第一页的东西。
        if (currentPageNo<1){
            currentPageNo = 1;
        }else if (currentPageNo>totalPageCount){//当前页面大于了最后一页。
            currentPageNo = totalPageCount;
        }

        //获取用户列表展示。
        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        req.setAttribute("userList",userList);

        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        req.setAttribute("roleList",roleList);
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);
        req.setAttribute("queryUserName",queryUserName);
        req.setAttribute("queryUserRole",queryUserRole);

        //返回前端
        try {
            req.getRequestDispatcher("userlist.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 从Session里面拿ID;
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String newpassword = req.getParameter("newpassword");
        boolean flag = false;
        System.out.println(newpassword);
        if (o != null && newpassword != null && newpassword.length() != 0) {
            System.out.println("jinrule");
            UserService userService = new UserServiceImpl();
            try {
                flag = userService.updatePwd(((User) o).getId(), newpassword);
                System.out.println("id=" + ((User) o).getId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (flag) {
                System.out.println("flag=ture");
                req.setAttribute("message", "修改密码成功，请退出，使用新密码登录");
//密码修改成功，移除当前Session
                req.getSession().removeAttribute(Constants.USER_SESSION);
                req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
                try{Thread.sleep(3000); //3秒
                } catch (Exception e){}
                resp.sendRedirect(req.getContextPath() + "/login.jsp");
            } else {
                req.setAttribute("message", "密码修改失败");
//密码修改成功，移除当前Session
            }
        } else {
            req.setAttribute("message", "新密码有问题");
            req.getRequestDispatcher("/jsp/pwdmodify.jsp").forward(req, resp);
        }
    }
    //验证旧密码,session中有用户的密码。
    public void pwdModify(HttpServletRequest req, HttpServletResponse resp){
        //从Session里面拿id；
        Object o = req.getSession().getAttribute(Constants.USER_SESSION);
        String oldpassword = req.getParameter("oldpassword");

        //万能的Map：结果集
        Map<String, String> resultMap = new HashMap<String, String>();

        if (o==null){//Session失效了，session过期了。
            resultMap.put("result","sessionerror");
        }else if (StringUtils.isNullOrEmpty(oldpassword)){//输入的密码为空。
            resultMap.put("result","error");
        }else {
            String userPassword = ((User)o).getUserPassword();//Session中用户的密码。
            if (oldpassword.equals(userPassword)){
                resultMap.put("result","true");
            }else {
                resultMap.put("result","false");
            }
        }

        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            //JSONArray 阿里巴巴的JSON工具类，转换格式。
            /*
            resultMap = ["reslut","sessionerror","reslut","error"]
            Json格式 = {key:value}
             */
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}

