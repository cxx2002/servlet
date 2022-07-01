package com.cxx.service.user;

import com.cxx.dao.BaseDao;
import com.cxx.dao.user.UserDao;
import com.cxx.dao.user.UserDaoImpl;
import com.cxx.pojo.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    public UserServiceImpl() {
        userDao = new UserDaoImpl();
    }

    private final UserDao userDao;//调用dao层的方法
    
    //根据id查找user
    public User getUserById(String id) {
        User user = null;
        Connection connection = null;
        try {
            connection = BaseDao.getConnection();
            user = userDao.getUserById(connection, id);
        } catch (Exception e) {
            e.printStackTrace();
            user = null;
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return user;
    }

    //修改用户信息
    public boolean modify(User user) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (userDao.modify(connection,user)>0){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    //删除用户
    public boolean del(Integer delId) {
        Connection connection = null;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            if (userDao.del(connection,delId) > 0){
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    //查询记录数
    public int getUserCount(String username, int userRole) {
        Connection connection = null;
        int count = 0;

        try {
            connection = BaseDao.getConnection();
            count = userDao.getUserCount(connection,username,userRole);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseDao.closeResource(connection,null,null);
        }

        return count;
    }

    //根据条件查询用户列表
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int pageSize) {
        Connection connection = null;
        List<User> userList = null;

        try {
            connection = BaseDao.getConnection();
            userList = userDao.getUserList(connection, queryUserName, queryUserRole, currentPageNo, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            BaseDao.closeResource(connection, null, null);
        }
        return userList;
    }

    //增加用户信息
    public boolean add(User user) {
        Connection connection = null;
        int addRows = 0;
        boolean flag = false;

        try {
            connection = BaseDao.getConnection();
            connection.setAutoCommit(false);//开启JDBC事务。//sql异常
            addRows = userDao.add(connection,user);//long异常
            connection.commit();//sql异常
            if (addRows>0){
                flag = true;
                System.out.println("add success!");
            }else {
                System.out.println("add failed!");
            }
        } catch (Exception e) {//失败了就会捕捉到异常
            e.printStackTrace();
            try {
                connection.rollback();
                System.out.println("========rollback=========");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } finally {
            //在service层进行connection连接的关闭.
            BaseDao.closeResource(connection,null,null);
        }
        return flag;
    }

    public boolean updatePwd(int id, String pwd) throws SQLException {
        //前端阿贾克斯已经做了旧密码验证，但是service层还是要在做一遍比较好
        boolean flag = false;
        System.out.println("jinrule updatepwd");
        Connection connection = BaseDao.getConnection();
        if(userDao.updatePwd(connection,id,pwd)>0){
            System.out.println("dao正确");
            flag=true;}
        BaseDao.closeResource(connection,null,null);
        return flag;
    }

    public User login(String userCode, String password) throws SQLException{
        Connection connection = BaseDao.getConnection();

        User user = null;

        user = userDao.getLoginUser(connection, userCode);

        //和数据库该用户的密码做对比
        if(!password.equals(user.getUserPassword()))
            return null;

        BaseDao.closeResource(connection,null,null);

        return user;
    }

}
