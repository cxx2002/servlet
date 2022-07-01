package com.cxx.dao.user;

import com.cxx.dao.BaseDao;
import com.cxx.pojo.User;
import com.mysql.cj.util.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    //根据id查找user
    public User getUserById(Connection connection, String id) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        User user = null;
        if (connection!=null){
            String sql = "select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.id=? and u.userRole = r.id";
            Object[] params = {id};
            rs = BaseDao.execute(connection,pstm,rs,sql,params);
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
                user.setUserRoleName(rs.getString("userRoleName"));
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return user;
    }

    //修改用户信息
    public int modify(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int modifyRows = 0;
        if (connection!=null){
            String sql = "update smbms_user set userName=?," +
                    "gender=?,birthday=?,phone=?,address=?,userRole=?,modifyBy=?,modifyDate=? where id = ? ";
            Object[] params = {user.getUserName(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getUserRole(), user.getModifyBy(),
                    user.getModifyDate(), user.getId()};
            modifyRows = BaseDao.execute(connection,pstm,sql,params);
            BaseDao.closeResource(null,pstm,null);
        }
        return modifyRows;
    }

    //删除用户
    public int del(Connection connection, Integer delId) throws SQLException {
        PreparedStatement pstm = null;
        int delRows = 0;
        String sql = "delete from smbms_user where id = ?";
        Object[] params = {delId};
        if(connection!=null){
            delRows =BaseDao.execute(connection,pstm,sql,params);
            BaseDao.closeResource(connection,null,null);
        }
        return delRows;
    }

    //增加用户信息
    public int add(Connection connection, User user) throws SQLException {
        PreparedStatement pstm = null;
        int addRows = 0;
        if (connection!=null){
            String sql = "insert into smbms_user (userCode,userName,userPassword," +
                    "userRole,gender,birthday,phone,address,creationDate,createdBy) " +
                    "values(?,?,?,?,?,?,?,?,?,?)";
            Object[] params = {user.getUserCode(), user.getUserName(), user.getUserPassword(),
                    user.getUserRole(), user.getGender(), user.getBirthday(),
                    user.getPhone(), user.getAddress(), user.getCreationDate(), user.getCreatedBy()};
            addRows = BaseDao.execute(connection,pstm,sql,params);
            BaseDao.closeResource(null,pstm,null);
        }
        return addRows;
    }

    //通过条件查询用户。（分页）
    public List<User> getUserList(Connection connection, String userName, int userRole, int currentPageNo, int pageSize) throws Exception {

        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<User> userList = new ArrayList<User>();

        if (connection != null) {
            StringBuffer sql = new StringBuffer();
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list = new ArrayList<Object>();

            if (!StringUtils.isNullOrEmpty(userName)) {
                sql.append(" and u.userName like ?");
                list.add("%" + userName + "%");
            }

            if (userRole > 0) {
                sql.append(" and u.userRole = ?");
                list.add(userRole);
            }

            //在mysql数据库中，分页使用 limit startIndex，pageSize ; 总数
            sql.append(" order by creationDate DESC limit ?,?");
            currentPageNo = (currentPageNo - 1) * pageSize;
            list.add(currentPageNo);
            list.add(pageSize);

            Object[] params = list.toArray();
            System.out.println("getUserList的sql语句：" + sql.toString());
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);
            while (rs.next()) {
                User _user = new User();
                _user.setId(rs.getInt("id"));
                _user.setUserCode(rs.getString("userCode"));
                _user.setUserName(rs.getString("userName"));
                _user.setGender(rs.getInt("gender"));
                _user.setBirthday(rs.getDate("birthday"));
                _user.setPhone(rs.getString("phone"));
                _user.setUserRole(rs.getInt("userRole"));
                _user.setUserRoleName(rs.getString("userRoleName"));
                userList.add(_user);
            }
            BaseDao.closeResource(null, pstm, rs);
        }
        return userList;
    }

    //根据用户名或者角色查询用户总数。（最难理解的sql）
    public int getUserCount(Connection connection,String username,int userRole) throws SQLException {
        PreparedStatement pstm = null;
        ResultSet rs = null;
        int count = 0;

        if (connection!=null){
            StringBuffer sql = new StringBuffer();
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole = r.id");
            ArrayList<Object> list = new ArrayList<Object>();//存放我们的参数。

            if (!StringUtils.isNullOrEmpty(username)){
                sql.append(" and u.userName = ?");
                list.add("%"+username+"%");//index:0   模糊查询。
            }

            if (userRole>0){
                sql.append(" and u.userRole = ?");
                list.add(userRole);//index:1
            }

            //怎么把list转换为数组。
            Object[] params = list.toArray();

            System.out.println("getUserCount的sql语句："+sql.toString());//输出最后完整的sql语句。
            rs = BaseDao.execute(connection, pstm, rs, sql.toString(), params);

            if (rs.next()){
                count = rs.getInt("count");//从结果集中获取最终的参数。
            }
            BaseDao.closeResource(null,pstm,rs);
        }
        return count;
    }


    //修改当前用户的密码
    public int updatePwd(Connection connection, int id, String password) throws SQLException {
        int execute=0;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            String sql = "update smbms_user set userPassword = ? where id = ?";
            Object[] params = {password,id};
            System.out.println(password+id);
            execute = BaseDao.execute(connection, preparedStatement, sql, params);
            System.out.println("execute="+execute);

            BaseDao.closeResource(connection, preparedStatement, null);
        }
        return execute;
    }

    //得到要登录的用户,因为在service层判断用户名和密码正误，在dao层就只需要通过用户名拿到用户就够了
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        User user = null;
        if (connection != null) {
            PreparedStatement preparedStatement = null;
            ResultSet rs = null;
            String sql = "select * from smbms_user where userCode=?";
            Object[] params = {userCode};

            rs = BaseDao.execute(connection, preparedStatement, rs, sql, params);

            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUserCode(rs.getString("userCode"));
                user.setUserName(rs.getString("userName"));
                user.setUserPassword(rs.getString("userPassword"));
                user.setGender(rs.getInt("gender"));
                user.setBirthday(rs.getDate("birthday"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setUserRole(rs.getInt("userRole"));
                user.setCreatedBy(rs.getInt("createdBy"));
                user.setCreationDate(rs.getTimestamp("creationDate"));
                user.setModifyBy(rs.getInt("modifyBy"));
                user.setModifyDate(rs.getTimestamp("modifyDate"));
            }

            BaseDao.closeResource(connection, preparedStatement, rs);
        }
        return user;
    }
}
