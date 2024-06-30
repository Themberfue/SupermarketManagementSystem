package org.marketsystem.blackmarket.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @ClassDescription:该类是用于增加数据的工具类
 * ，通过连接数据库在数据库中加入数据
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/20 10:03
 */
public class AddData {
    public static int i;
    /**
     * @description:用于增加商品信息
     * @author: Pxoolcm
     * @date: 2024/6/21 10:40
     * @param: [productId, productName, productDesc, productLoca, catagoryId, supplierId, price, stocks]
     * @return: void
     **/
    public static void addProduct(String productId,String productName
            ,String productDesc,String productLoca,int catagoryId
            ,int supplierId,double price,int stocks) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "insert into product values(?,?,?,?,?,?,?,?)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,productId);
        ppst.setString(2,productName);
        ppst.setString(3,productDesc);
        ppst.setString(4,productLoca);
        ppst.setInt(5,catagoryId);
        ppst.setInt(6,supplierId);
        ppst.setDouble(7,price);
        ppst.setInt(8,stocks);
        i = ppst.executeUpdate();
        System.out.println(i);
        MysqlCon.closeCon(conn,ppst);
    }
    /**
     * @description:增加订单的方法
     * @author: Pxoolcm
     * @date: 2024/6/21 10:48
     * @param: [order_id, cus_id, cus_name, order_date, money]
     * @return: void
     **/
    public static void addOrder(int order_id,int cus_id,String cus_name
            ,String order_date,double money) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "insert into aorder values (?,?,?,?,?)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,order_id);
        ppst.setInt(2,cus_id);
        ppst.setString(3,cus_name);
        ppst.setString(4,order_date);
        ppst.setDouble(5,money);
        int i1 = ppst.executeUpdate();
        System.out.println(i1);
        MysqlCon.closeCon(conn,ppst);
    }

    public static void addCustomer(int username,String pwd) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "INSERT INTO customer(customer_id,password,is_vip) VALUES (?,?,0)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,username);
        ppst.setString(2,pwd);
        int i1 = ppst.executeUpdate();
        System.out.println(i1);
        MysqlCon.closeCon(conn,ppst);
    }

    public static void addPlusCustomer(int username,String pwd) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "INSERT INTO customer(customer_id,password,is_vip) VALUES (?,?,1)";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,username);
        ppst.setString(2,pwd);
        int i1 = ppst.executeUpdate();
        System.out.println(i1);
        MysqlCon.closeCon(conn,ppst);
    }

}
