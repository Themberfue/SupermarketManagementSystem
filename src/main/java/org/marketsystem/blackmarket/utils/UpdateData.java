package org.marketsystem.blackmarket.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author: Themberfue
 * @date: 2024/6/20 23:40
 * @description:
 */
public class UpdateData {
    public static void updateProduct(String product_id,String product_name
            ,String product_desc,String product_loca,int catagory_id
            ,int supplier_id,double price,int stocks) throws SQLException, ClassNotFoundException {
        String sql = "update product set " +
                "product_name = ?,product_description = ?" +
                ",product_location = ?,category_id = ?" +
                ",supplier_id = ?,price = ?,stocks = ? " +
                "where product_id = ?";
        Connection conn = MysqlCon.Connection();
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,product_name);
        ppst.setString(2,product_desc);
        ppst.setString(3,product_loca);
        ppst.setInt(4,catagory_id);
        ppst.setInt(5,supplier_id);
        ppst.setDouble(6,price);
        ppst.setInt(7,stocks);
        ppst.setString(8,product_id);
        int i = ppst.executeUpdate();
        System.out.println(i);
        MysqlCon.closeCon(conn,ppst);


    }
    public static void updateOrder(int order_id,int cus_id,String cus_name
            ,String order_date,double money) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "update aorder set " +
                "customer_id = ?" +
                ",customer_name = ?,order_date = ?" +
                ",money = ? where order_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,cus_id);
        ppst.setString(2,cus_name);
        ppst.setString(3,order_date);
        ppst.setDouble(4,money);
        ppst.setInt(5,order_id);
        int i = ppst.executeUpdate();
        System.out.println(i);
        MysqlCon.closeCon(conn,ppst);
    }

    public static void updateIsVip(int customerId,boolean isVip) throws SQLException, ClassNotFoundException {
        String sql = null;
        if (isVip) {
            sql = "UPDATE customer SET is_vip = 1 WHERE customer_id = ?";
        } else {
            sql = "UPDATE customer SET is_vip = 0 WHERE customer_id = ?";
        }
        Connection conn = MysqlCon.Connection();
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1, customerId);
            int i = ppst.executeUpdate();


    }

    public static void updateCustomerInfo(String customerId,String customerName,String address,String email,String phone) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "UPDATE customer SET customer_name = ?,address = ?,email = ?,phone = ? WHERE customer_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,customerName);
        ppst.setString(2,address);
        ppst.setString(3,email);
        ppst.setString(4,phone);
        ppst.setInt(5,Integer.parseInt(customerId));
        ppst.executeUpdate();
    }
}
