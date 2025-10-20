package com.abcnews.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBContext {
    // Thông tin JDBC (theo snippet bạn gửi). Nếu database của bạn là HRM thì đổi databaseName = "HRM".
    static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    static String server = "localhost";
    static String databaseName = "News"; // <- đổi sang HRM nếu bạn dùng HRM
    static String dburl = "jdbc:sqlserver://" + server + ";databaseName=" + databaseName + ";encrypt=false";
    static String username = "sa";
    static String password = "123456";

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        return DriverManager.getConnection(dburl, username, password);
    }
}
