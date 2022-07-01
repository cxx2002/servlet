package com.cxx.service.Provider;

import com.cxx.pojo.Provider;

import java.sql.SQLException;
import java.util.List;

public interface ProviderService {
    //通过查询条件获取供应商列表-模糊查询-getProviderList
    public List<Provider> getProviderList(String proName, String proCode,int currentPageNo, int pageSize) throws Exception;
    //查询供应商总数
    public int getUserCount() throws SQLException;
}
