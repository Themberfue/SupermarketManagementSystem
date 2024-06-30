package org.marketsystem.blackmarket.cashierManage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.marketsystem.blackmarket.dataSheet.Order;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.MysqlCon;
import org.marketsystem.blackmarket.utils.SearchData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * @className: CustomOrderView
 * @author: 朝槿
 * @date: 2024/6/20 10:44
 */
public class CustomerOrderView extends StackPane {
    static TableView<Order> tableView = new TableView<>();
    List<Order> orders;
    public CustomerOrderView() {
        Label title = new Label("客户订单查询");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 30));

        //创建一个文本输入框用于搜索
        TextField searchField = new TextField();
        searchField.setPromptText("搜索订单...");
        searchField.setMinWidth(850);
        searchField.setStyle("-fx-font-size: 13px; -fx-padding: 8px;");

        // 创建搜索按钮
        Button searchButton = new Button("搜索", createImageView("/search_icon_white.png"));
        searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));

        // 创建水平盒子用于容纳搜索框和按钮
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(20);
        searchBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-padding: 13px");

        // 创建刷新按钮
        Button refreshButton = new Button("刷新表格数据", createImageView("/refresh_icon.png"));
        refreshButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));
        refreshButton.setStyle(
                "-fx-background-color: #336699; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        );

        // 添加悬停效果
        refreshButton.setOnMouseEntered(e -> refreshButton.setStyle(
                "-fx-background-color: #274b73; " +  // 悬停时颜色变化
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));
        refreshButton.setOnMouseExited(e -> refreshButton.setStyle(
                "-fx-background-color: #336699; " +  // 恢复默认样式
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));

        // 存放刷新按钮盒子
        HBox infoBox = new HBox();
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10));
        infoBox.setSpacing(20);
        infoBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px;");
        infoBox.getChildren().addAll(refreshButton);

        // 创建标签
        Label orderIdLabel = new Label("订单号");
        Label customerIdLabel = new Label("客户Id");
        Label customerNameLabel = new Label("客户姓名");
        Label orderDateLabel = new Label("订单日期");
        Label moneyLabel = new Label("金额");
        Label statusOrderLabel = new Label("订单状态");

        // 设置标签样式
        String tdLabelStyle = "-fx-font-size: 16px; -fx-padding: 10px; -fx-font-weight: Bold";
        orderIdLabel.setStyle(tdLabelStyle);
        customerIdLabel.setStyle(tdLabelStyle);
        customerNameLabel.setStyle(tdLabelStyle);
        orderDateLabel.setStyle(tdLabelStyle);
        moneyLabel.setStyle(tdLabelStyle);
        statusOrderLabel.setStyle(tdLabelStyle);

        // 创建可伸缩的区域用于均分标签
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        Region spacer5 = new Region();

        // 设置 HBox 的 HGrow 属性以均分标签
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        HBox.setHgrow(spacer5, Priority.ALWAYS);

        // 创建 HBox 并添加标签和可伸缩区域
        HBox tdHbox = new HBox(10, orderIdLabel, spacer1, customerIdLabel, spacer2, customerNameLabel, spacer3, orderDateLabel, spacer4, moneyLabel, spacer5, statusOrderLabel);
        tdHbox.setAlignment(Pos.CENTER);

        // 设置 HBox 样式
        tdHbox.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px");

        VBox thVBox = new VBox(10);

        VBox layout = new VBox(10, title, searchBox, infoBox, tdHbox, thVBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        orders = new ArrayList<>();

        refreshButton.setOnAction(e -> refreshData(orders, thVBox));
        searchButton.setOnAction(e -> {
            searchOrderData(searchField, thVBox);
            searchField.setText("");
        });

        refreshData(orders, thVBox);

        this.getChildren().add(layout);
    }

    private static HBox createOrderInfo(Order order) {
        String thLabelStyle = "-fx-font-size: 16px; -fx-padding: 10px; -fx-font-weight: Normal";

        // 创建标签
        Label orderIdLabel = new Label(String.valueOf(order.getOrder_id()));
        Label customerIdLabel = new Label(String.valueOf(order.getCustomer_id()));
        Label customerNameLabel = new Label(order.getCustomer_name());
        Label orderDateLabel = new Label(order.getOrder_date());
        Label moneyLabel = new Label(String.valueOf(order.getMoney()));
        Label statusOrderLabel = new Label(order.isStatus() ? "已出库" : "未出库");

        // 设置标签样式
        orderIdLabel.setStyle(thLabelStyle);
        customerIdLabel.setStyle(thLabelStyle);
        customerNameLabel.setStyle(thLabelStyle);
        orderDateLabel.setStyle(thLabelStyle);
        moneyLabel.setStyle(thLabelStyle);

        // 设置状态标签的颜色
        if (!order.isStatus()) {
            statusOrderLabel.setStyle(thLabelStyle + "; -fx-text-fill: red;");
        } else {
            statusOrderLabel.setStyle(thLabelStyle + "; -fx-text-fill: green;");
        }

        // 创建可伸缩的区域用于均分标签
        Region spacer1 = new Region();
        Region spacer2 = new Region();
        Region spacer3 = new Region();
        Region spacer4 = new Region();
        Region spacer5 = new Region();

        // 设置 HBox 的 HGrow 属性以均分标签
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        HBox.setHgrow(spacer4, Priority.ALWAYS);
        HBox.setHgrow(spacer5, Priority.ALWAYS);

        // 创建 HBox 并添加标签和可伸缩区域
        HBox thHbox = new HBox(10, orderIdLabel, spacer1, customerIdLabel, spacer2, customerNameLabel, spacer3, orderDateLabel, spacer4, moneyLabel, spacer5, statusOrderLabel);
        thHbox.setAlignment(Pos.CENTER);

        // 设置 HBox 样式
        thHbox.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 10px; -fx-border-color: #ccc; -fx-border-width: 1px; -fx-border-radius: 5px");

        return thHbox;
    }
    // 快捷创建图像
    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }

    private static void refreshData(List<Order> orders, VBox tdVbox) {
        tdVbox.getChildren().clear();
        orders.clear();
        Boolean[] flags = new Boolean[]{true, false};
        Random random = new Random();
        try {
            String sql = "SELECT * FROM aorder";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ResultSet resultSet = ppst.executeQuery();
            while (resultSet.next()) {
                int randomNumber = random.nextInt(2);
                Order order = new Order(resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("order_date"),
                        resultSet.getDouble("money"),
                        flags[randomNumber]);
                orders.add(order);
            }
            // 创建并添加每个订单信息
            for (Order order : orders) {
                tdVbox.getChildren().add(createOrderInfo(order));
            }
            MysqlCon.closeCon(conn,ppst,resultSet);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private static void refreshOrderData(String searchText) throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM aorder WHERE order_id = ?";
        Connection conn = MysqlCon.Connection();
        PreparedStatement ppst = conn.prepareStatement(sql);
        ppst.setInt(1,Integer.parseInt(searchText));
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
        MysqlCon.closeCon(conn,ppst,resultSet);
        tableView.setItems(list);
    }

    private void searchOrderData(TextField searchField, VBox layout) {
        try {
            if (SearchData.searchOrderId(searchField.getText())){
                orders.clear();
                orders = SearchData.Orderlist;
                refreshSearchOrderData(orders, layout);
            } else {
                MyAlert.alertSet("查找订单","失败","!请输入存在的订单信息!");
            }
        } catch (SQLException ex) {
            MyAlert.alertSet("查找订单","失败","!请输入存在的订单信息!");
        } catch (ClassNotFoundException | NumberFormatException e) {
            MyAlert.alertSet("查找订单","失败","!请输入正确的订单Id格式!");
        }
    }
    private void refreshSearchOrderData(List<Order> order1, VBox thVBox) {
        thVBox.getChildren().clear();
        orders = order1;
        // 创建并添加每个订单信息
        for (Order order : orders) {
            thVBox.getChildren().add(createOrderInfo(order));
        }
    }
}
