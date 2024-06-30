package org.marketsystem.blackmarket.utils;

import java.sql.*;

import static org.marketsystem.blackmarket.loginAndRegister.login.password;
import static org.marketsystem.blackmarket.loginAndRegister.login.username;

/**
 * @ClassDescription:通过数据库连接用于验证仓库管理员和收银员的密码
 * @JdkVersion: 2.1
 * @Author: 廖春花
 *
 * @Created: 2024/6/19 15:32
 */
public class Verify {
    /**
     * @description:验证管理员登录函数
     * @author: Pxoolcm
     * @date: 2024/6/24 9:08
     * @param: []
     * @return: boolean
     **/
    public static boolean verifyManager() throws SQLException, ClassNotFoundException {
        // 得到链接
        try (Connection managerConn = new MysqlCon().Connection();
             // 编写 SQL 语句
             PreparedStatement pst = managerConn.prepareStatement(
                     "SELECT 1 FROM warehouse_staff WHERE staff_name = ? AND password = ?")) {

            // 设置参数
            pst.setString(1, username);
            pst.setString(2, password);

            // 执行查询并检查是否有结果
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // 如果结果集中有数据，则证明用户名和密码匹配
            }
        }
    }
    /**
     * @description:验证收银员登录函数
     * @author: Pxoolcm
     * @date: 2024/6/24 9:08
     * @param: []
     * @return: boolean
     **/
    public static boolean verifyCashier() throws SQLException, ClassNotFoundException {
        try (Connection managerConn = new MysqlCon().Connection();
             // 编写 SQL 语句
             PreparedStatement pst = managerConn.prepareStatement(
                     "SELECT 1 FROM cashier WHERE cashier_name = ? AND password = ?")) {

            // 设置参数
            pst.setString(1, username);
            pst.setString(2, password);

            // 执行查询并检查是否有结果
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next(); // 如果结果集中有数据，则证明用户名和密码匹配
            }
        }
    }
    /**
     * @description:验证Plus用户登录函数
     * @author: Pxoolcm
     * @date: 2024/6/24 9:08
     * @param: []
     * @return: boolean
     **/

    public static boolean verifyPlusCustomer() throws SQLException, ClassNotFoundException {
        Connection conn = MysqlCon.Connection();
        String sql = "SELECT 1 FROM customer WHERE customer_id = ? AND password = ? AND is_vip = 1";

        PreparedStatement ppst = conn.prepareStatement(sql);
        try {
            ppst.setInt(1,Integer.parseInt(username));
        } catch (SQLException e) {
            System.out.println("错误已经出现在前端窗口了");
        } catch (NumberFormatException e) {
            MyAlert.alertSet("登录","失败","请输入正确的账号格式！（只允许整数！）");
        }
        ppst.setString(2,password);
        try(ResultSet resultSet = ppst.executeQuery()) {
            return resultSet.next();
        }
    }
    /**
     * @description:验证普通用户登录函数
     * @author: Pxoolcm
     * @date: 2024/6/24 9:09
     * @param: []
     * @return: boolean
     **/
    public static boolean verifyCustomer() throws SQLException, ClassNotFoundException {

        Connection conn = MysqlCon.Connection();
        String sql = "SELECT 1 FROM customer WHERE customer_id = ? AND password = ? AND is_vip = 0";

        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,Integer.parseInt(username));
        ppst.setString(2,password);
        try(ResultSet resultSet = ppst.executeQuery()) {
            return resultSet.next();
        }
    }
}
