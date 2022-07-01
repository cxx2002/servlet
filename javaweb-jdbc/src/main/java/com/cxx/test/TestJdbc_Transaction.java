package com.cxx.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestJdbc_Transaction {
    public static void main(String[] args) {
        //配置信息
//useUnicode=true&characterEncoding=utf-8解决中文乱码
        String url = "jdbc:mysql://localhost:3306/people?useUnicode=true&characterEncoding=UTF-8" +
                "&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456";

        Connection connection=null;
//1.加载驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
//2.连接数据库,代表数据库
           connection = DriverManager.getConnection(url, username, password);

            //3.通知数据库开启事务，false开启------等价与sql语句start transaction;
            connection.setAutoCommit(false);//关闭事务自动提交，就会开启事务模式

            String sql="update account set money=money - 100 where name = 'A'";
            connection.prepareStatement(sql).executeUpdate();

            //制造错误
            //int i=1/0;

            String sql2="update account set money=money + 100 where name = 'B'";
            connection.prepareStatement(sql2).executeUpdate();

            //手动提交事务
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
