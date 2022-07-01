package com.cxx.servlet.provider;

import com.cxx.pojo.Provider;
import com.cxx.service.Provider.ProviderService;
import com.cxx.service.Provider.ProviderServiceImpl;
import com.cxx.util.PageSupport;
import com.mysql.cj.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String queryProName = req.getParameter("queryProName");
        String queryProCode = req.getParameter("queryProCode");
        String pageIndex = req.getParameter("pageIndex");
        if (StringUtils.isNullOrEmpty(queryProName)) {
            queryProName = "";
        }
        if (StringUtils.isNullOrEmpty(queryProCode)) {
            queryProCode = "";
        }
        List<Provider> providerList = new ArrayList<Provider>();
        ProviderService providerService = new ProviderServiceImpl();
        //第一次走这个请求，一定是第一页，页面大小固定的：
        int pageSize = 5;//可以把这个配置到配置文件中，方便后期修改；
        int currentPageNo = 1;


        if (pageIndex!=null){
            currentPageNo = Integer.parseInt(pageIndex);
        }

        //获取用户的总量（分页：上一页，下一页的情况）
        int totalCount = 0;
        try {
            totalCount = providerService.getUserCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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


        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);


        try {
            providerList = providerService.getProviderList(queryProName, queryProCode,currentPageNo,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("providerList", providerList);
        req.setAttribute("queryProName", queryProName);
        req.setAttribute("queryProCode", queryProCode);
        req.getRequestDispatcher("providerlist.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
