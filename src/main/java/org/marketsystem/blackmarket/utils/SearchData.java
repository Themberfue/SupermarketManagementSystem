package org.marketsystem.blackmarket.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import org.marketsystem.blackmarket.dataSheet.Customer;
import org.marketsystem.blackmarket.dataSheet.Order;
import org.marketsystem.blackmarket.dataSheet.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassDescription:该类是用于查询的工具类
 * ，用于连接数据库然后通过各种条件查询数据
 * 查询商品：1.通过供应商筛选查询 2.通过商品类别筛选查询
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/20 8:44
 */
public class SearchData {
    public static List<Order> Orderlist = new ArrayList<>();
    //按照供应商筛选查找
    public static void SearchProductsBySupplier(String supplier, TableView<Product> table) throws SQLException, ClassNotFoundException {
        int supplierId = 0;
        switch (supplier){
            case "瓦伦丁": supplierId = 1; break;
            case "草莓镇": supplierId = 2; break;
            case "圣丹尼斯": supplierId = 3; break;
            case "罗兹镇": supplierId = 4;
        }
        Connection conn = MysqlCon.Connection();
        String sql = "select * from product where supplier_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,supplierId);

        setTableProductDataView(ppst, table);
        MysqlCon.closeCon(conn,ppst);
    }

    //按照商品种类筛选查找
    public static void SearchProductsByCatagory(String catagory, TableView<Product> table) throws SQLException, ClassNotFoundException {
        int catagoryId = 0;
        switch (catagory){
            case "服装" : catagoryId = 1; break;
            case "药品及药剂" :catagoryId = 2; break;
            case "杂货及干货" :catagoryId = 3; break;
        }
        Connection conn = MysqlCon.Connection();
        String sql = "select * from product where category_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,catagoryId);

        setTableProductDataView(ppst, table);
        MysqlCon.closeCon(conn,ppst);
    }

    //按照商品id查找
    public static boolean SearchProductsById(String productId) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from product where product_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setString(1,productId);
        ResultSet rs = ppst.executeQuery();
        boolean isEixstNext = rs.next();
        rs.close();
        MysqlCon.closeCon(conn,ppst);
        return isEixstNext;
    }

    private static void setTableProductDataView(PreparedStatement ppst, TableView<Product> table) throws SQLException {
        ResultSet resultSet = ppst.executeQuery();
        ObservableList<Product> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Product product = new Product(resultSet.getString("product_id"),
                    resultSet.getString("product_name"),
                    resultSet.getString("product_description"),
                    resultSet.getString("product_location"),
                    resultSet.getInt("category_id"),
                    resultSet.getInt("supplier_id"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("stocks"));
            list.add(product);
        }
        table.setItems(list);
        resultSet.close();
    }
    public static boolean SearchCustomerById(int id, TableView<Customer> table) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "select * from customer where customer_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,id);
        if (ppst.executeQuery().next()){
            setTableCustomerDataView(ppst,table);
            System.out.println(11111111);
            MysqlCon.closeCon(conn,ppst);
            return true;
        }
        MysqlCon.closeCon(conn,ppst);
        return false;
    }
    private static void setTableCustomerDataView(PreparedStatement ppst, TableView<Customer> table) throws SQLException {
        ResultSet resultSet = ppst.executeQuery();
        ObservableList<Customer> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Customer customer = new Customer(resultSet.getInt("customer_id")
                    ,resultSet.getString("customer_name")
                    ,resultSet.getString("phone")
                    ,resultSet.getString("email")
                    ,resultSet.getString("address")
                    ,resultSet.getBoolean("is_vip")
                    ,resultSet.getString("password"));
            list.add(customer);
        }
        table.setItems(list);
        resultSet.close();
    }

    public static int NumberOfCustoms() throws SQLException, ClassNotFoundException {
        int num = 0;
        Connection conn = MysqlCon.Connection();
        String sql = "SELECT * FROM customer";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ResultSet resultSet = ppst.executeQuery();
        while (resultSet.next()){
            num++;
        }
        MysqlCon.closeCon(conn,ppst,resultSet);
        return num;
    }

    public static int NumberOfPlusCustomer() throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        int num = 0;
        String sql = "SELECT * FROM customer WHERE is_vip = 1";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ResultSet resultSet = ppst.executeQuery();
        while (resultSet.next()){
            num++;
        }
        MysqlCon.closeCon(conn,ppst,resultSet);
        return num;
    }

    public static boolean searchOrderId(String orderId) throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "SELECT * FROM aorder WHERE order_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,Integer.parseInt(orderId));
        Orderlist = setTableOrderDataView(ppst);
        //System.out.println(ppst.executeQuery().next());
        return ppst.executeQuery().next();
    }

    private static List<Order> setTableOrderDataView(PreparedStatement ppst) throws SQLException{
        ResultSet resultSet = ppst.executeQuery();
        ObservableList<Order> list = FXCollections.observableArrayList();
        while (resultSet.next()) {
            Order order = new Order(resultSet.getInt("order_id")
                    ,resultSet.getInt("customer_id")
                    ,resultSet.getString("customer_name")
                    ,resultSet.getString("order_date")
                    ,resultSet.getDouble("money"));
            list.add(order);
        }
        resultSet.close();
        return list;
    }

    public static List<Customer> setTableCustomerDataView(String userName) throws SQLException, ClassNotFoundException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        Connection conn = MysqlCon.Connection();
        String sql = "SELECT * FROM customer WHERE customer_id = ?";
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,Integer.parseInt(userName));
        ResultSet resultSet = ppst.executeQuery();
        while (resultSet.next()){
            Customer customer = new Customer(resultSet.getInt("customer_id")
                    , resultSet.getString("customer_name")
                    , resultSet.getString("phone")
                    , resultSet.getString("email")
                    , resultSet.getString("address")
                    , resultSet.getBoolean("is_vip")
                    , resultSet.getString("password"));
            list.add(customer);
        }
        MysqlCon.closeCon(conn,ppst,resultSet);
        return list;
    }
}
