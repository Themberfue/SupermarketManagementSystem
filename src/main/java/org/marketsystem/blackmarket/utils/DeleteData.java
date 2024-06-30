package org.marketsystem.blackmarket.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassDescription:该类是删除数据的工具类
 * ，用于连接数据库之后找到相关数据从数据库删除
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/20 10:32
 */
public class DeleteData {
    //删除商品信息
    public static boolean deleteProduct(String productId) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "delete from product where product_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,productId);
        int i = ppst.executeUpdate();
        System.out.println(i);
        MysqlCon.closeCon(conn,ppst);
        if (i<=0) return false;
        return true;
    }

    //删除订单信息
    public static void deleteOrder(int order_id) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "delete from aorder where order_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,order_id);
        int i = ppst.executeUpdate();
        System.out.println(i);
        MysqlCon.closeCon(conn,ppst);

    }
}
