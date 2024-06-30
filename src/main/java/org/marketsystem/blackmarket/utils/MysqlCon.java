package org.marketsystem.blackmarket.utils;

import java.sql.*;

/**
 * @ClassDescription:
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/19 15:35
 */
public class MysqlCon {
    /**
     * @description:mysql连接
     * @author: Pxoolcm
     * @date: 2024/6/21 14:28
     * @param: []
     * @return: java.sql.Connection
     **/
    public static Connection Connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/blackmarket?useSSL=false&serverTimezone=Asia/Shanghai";
        String user = "root";
        String password = "llc";
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;

    }
    /**
     * @description:关闭Connection和PreparedStatement
     * @author: Pxoolcm
     * @date: 2024/6/21 14:31
     * @param: [conn, ppst]
     * @return: void
     **/
    public static void closeCon(Connection conn, PreparedStatement ppst) throws SQLException {
        ppst.close();
        conn.close();
    }
    /**
     * @description:关闭Connection和PreparedStatement，ResultSet
     * @author: Pxoolcm
     * @date: 2024/6/21 14:31
     * @param: [conn, ppst, rs]
     * @return: void
     **/
    public static void closeCon(Connection conn, PreparedStatement ppst, ResultSet rs) throws SQLException {
        rs.close();
        ppst.close();
        conn.close();
    }
}
