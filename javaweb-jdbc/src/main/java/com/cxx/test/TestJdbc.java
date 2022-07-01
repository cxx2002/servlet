package com.cxx.test;

import java.sql.*;

public class TestJdbc {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //配置信息
//useUnicode=true&characterEncoding=utf-8解决中文乱码
        String url="jdbc:mysql://localhost:3306/people?useUnicode=true&characterEncoding=UTF-8" +
                "&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "123456";
//1.加载驱动
        Class.forName ("com.mysql.cj.jdbc.Driver");
//2.连接数据库,代表数据库
        Connection connection = DriverManager.getConnection(url, username, password);
//3.向数据库发送SQL的对象Statement : CRUD
        Statement statement = connection.createStatement();
//4.编写SQL
        String sql = "select * from people";
//5.执行查询SQL,返回一个ResultSet :结果集
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()){
            System. out . println("id="+rs. getObject(  "id"));
            System. out. println("name="+rs. getObject(  "name"));
            System. out. println("age="+rs. getObject(  "age"));
            System . out. println("address="+rs.getObject( "address"));

        }
//6.关闭连接，释放资源(- -定要做)先开后关
        rs.close();
        statement.close();
        connection.close();
    }
}
