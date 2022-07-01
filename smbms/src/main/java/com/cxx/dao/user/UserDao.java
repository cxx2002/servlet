package com.cxx.dao.user;

import com.cxx.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //得到要登录的用户
    public User getLoginUser(Connection connection, String userCode) throws SQLException;
    //修改当前用户密码
    public int updatePwd(Connection connection, int id,String password) throws SQLException;
    //根据用户名或角色查询用户总数
    public int getUserCount(Connection connection,String username,int userRole) throws SQLException;
    //通过条件查询用户。（分页）
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception;
    //增加用户信息
    public int add(Connection connection,User user) throws SQLException;
    //删除用户
    public int del(Connection connection,Integer delId) throws SQLException;
    //修改用户信息
    public int modify(Connection connection, User user)throws  SQLException;
    //根据id查找user
    public User getUserById(Connection connection, String id) throws SQLException;
}
