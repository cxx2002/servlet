package com.cxx.dao.role;

import com.cxx.pojo.Role;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public interface RoleDao {
    //获取角色列表
    public ArrayList<Role> getRoleList(Connection connection) throws SQLException;
}
