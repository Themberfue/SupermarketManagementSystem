package org.marketsystem.blackmarket.utils;

import org.marketsystem.blackmarket.dataSheet.Order;
import org.marketsystem.blackmarket.dataSheet.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassDescription:
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/21 8:48
 */
public class DataToObjects {
    public static Product dataToProductById(String product_id) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from product where product_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1, product_id);
        ResultSet resultSet = ppst.executeQuery();
        Product product = null;
        while (resultSet.next()) {
            product = new Product(resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("product_location"),
                    resultSet.getInt("category_id"),
                    resultSet.getInt("supplier_id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stocks"));
        }
        MysqlCon.closeCon(conn, ppst);
        return product;
    }

    public static Product dataToProductByName(String product_name) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from product where product_name = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1, product_name);
        ResultSet resultSet = ppst.executeQuery();
        Product product = null;
        while (resultSet.next()) {
            product = new Product(resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("product_location"),
                    resultSet.getInt("category_id"),
                    resultSet.getInt("supplier_id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stocks"));
        }
        MysqlCon.closeCon(conn, ppst);
        return product;
    }

    public static Order dataToOrderByOrderId(String order_id) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from aorder where order_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1, order_id);
        ResultSet resultSet = ppst.executeQuery();
        Order order = null;
        while (resultSet.next()) {
            order = new Order(resultSet.getInt("order_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("order_date"),
                    resultSet.getDouble("money"));
        }
        MysqlCon.closeCon(conn, ppst);
        return order;
    }

    public static Order dataToOrderByCustomerId(String customer_id) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from aorder where customer_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1, customer_id);
        ResultSet resultSet = ppst.executeQuery();
        Order order = null;
        while (resultSet.next()) {
            order = new Order(resultSet.getInt("order_id"),
                    resultSet.getInt("customer_id"),
                    resultSet.getString("customer_name"),
                    resultSet.getString("order_date"),
                    resultSet.getDouble("money"));
        }
        MysqlCon.closeCon(conn, ppst);
        return order;
    }
}

/**
 * @BelongsProject: BlackMarket
 * @BelongsPackage: org.marketsystem.blackmarket.tools
 * @Author: Pxoolcm
 * @CreateTime: 2024-06-21  08:48
 * @Description: TODO
 * @Version: 1.0
 */
