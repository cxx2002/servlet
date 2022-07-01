package com.cxx.dao.provider;

import com.cxx.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ProviderDao {
    /**
     * 通过查询条件获取供应商列表-模糊查询-getProviderList
     */
    List<Provider> getProviderList(Connection connection, String proName, String proCode,int currentPageNo, int pageSize) throws Exception;

    //查询供应商总数
    public int getProCount(Connection connection) throws SQLException;
}
