package com.cxx.test;

import java.sql.*;

public class TestJdbc_add {
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
//3.编写SQL
        String sql = "insert into people(id, name, age, address) VALUES (?,?,?,?)";
//4.预编译
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setInt(1,5);
        preparedStatement.setString(2,"小刘");
        preparedStatement.setInt(3,25);
        preparedStatement.setDate(4,new Date(new java.util.Date().getTime()));
//5.执行sql
        int i = preparedStatement.executeUpdate();
        if(i>0)
            System.out.println("插入成功！");
//6.关闭连接，释放资源(- -定要做)先开后关
        preparedStatement.close();
        connection.close();
    }
}
