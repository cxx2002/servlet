package com.cxx.dao.role;

import com.cxx.dao.BaseDao;
import com.cxx.pojo.Role;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RoleDaoImpl implements RoleDao{
    //获取角色列表。
    public ArrayList<Role> getRoleList(Connection connection) throws SQLException {

        PreparedStatement pstm = null;
        ResultSet resultSet = null;
        ArrayList<Role> rolelist = new ArrayList<>();

        if (connection!=null){
            String sql = "select * from smbms_role";
            Object[] params = {};
            resultSet = BaseDao.execute(connection, pstm, resultSet, sql, params);

            while (resultSet.next()){
                Role role = new Role();
                role.setId(resultSet.getInt("id"));
                role.setRoleCode(resultSet.getString("roleCode"));
                role.setRoleName(resultSet.getString("roleName"));
                rolelist.add(role);
            }
            BaseDao.closeResource(null,pstm,resultSet);
        }
        return rolelist;
    }
}
