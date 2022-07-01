package com.cxx.service.user;

import com.cxx.pojo.User;

import java.sql.SQLException;
import java.util.List;

public interface UserService {
    //用户登录
    public User login(String userCode,String password) throws SQLException;
    //根据用户id修改密码
    public boolean updatePwd(int id, String pwd) throws SQLException;
    //查询记录数
    public int getUserCount(String username,int userRole);
    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize);
    //增加用户信息
    public boolean add(User user);
    //删除用户
    public boolean del(Integer delId);
    //修改用户信息
    public boolean modify(User user);
    //根据id查找user
    public User getUserById(String id);
}
