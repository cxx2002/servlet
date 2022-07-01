package com.cxx.service.role;

import com.cxx.dao.BaseDao;
import com.cxx.dao.role.RoleDao;
import com.cxx.dao.role.RoleDaoImpl;
import com.cxx.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class RoleServiceImpl implements RoleService {
    //引入Dao。
    private RoleDao roleDao;
    public RoleServiceImpl() {
        roleDao = new RoleDaoImpl();
    }

    //获取角色列表。


    public List<Role> getRoleList() {

        Connection connection = null;
        List<Role> roleList = null;

        try {
            connection = BaseDao.getConnection();
            roleList = roleDao.getRoleList(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return roleList;
    }

}
