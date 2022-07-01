package com.cxx.service.Provider;

import com.cxx.dao.BaseDao;
import com.cxx.dao.provider.ProviderDao;
import com.cxx.dao.provider.ProviderDaoImpl;
import com.cxx.pojo.Provider;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProviderServiceImpl implements ProviderService{
    private ProviderDao providerDao;
    //private BillDao billDao;

    public ProviderServiceImpl() {
        providerDao = new ProviderDaoImpl();
        //billDao = new BillDaoImpl();
    }

    //查询供应商总数
    public int getUserCount() throws SQLException {
        Connection connection = null;
        connection=BaseDao.getConnection();
        int count = providerDao.getProCount(connection);
        return count;
    }

    //通过查询条件获取供应商列表-模糊查询-getBillList
    public List<Provider> getProviderList(String proName, String proCode,int currentPageNo, int pageSize) throws Exception {
        Connection connection =null;
        connection = BaseDao.getConnection();
        List<Provider> providerList=new ArrayList<>();
        providerList= providerDao.getProviderList(connection,proName,proCode,currentPageNo,pageSize);
        BaseDao.closeResource(connection,null,null);
        return providerList;
    }
}
