package org.marketsystem.blackmarket.warehouseManage;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.marketsystem.blackmarket.base.View;
import org.marketsystem.blackmarket.dataSheet.Order;
import org.marketsystem.blackmarket.utils.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * @author: Themberfue
 * @date: 2024/6/20 12:58
 * @description:
 */

public class OrdersManageView extends StackPane implements View {
    // 声明table为类成员变量
    private TableView<Order> tableView;

    // 构建整体页面
    public OrdersManageView() {
        // 创建标题
        Label title = new Label("订单管理");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        title.setTextFill(Color.BLACK);

        // 创建表格视图
        tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Order, Integer> orderIdColumn = new TableColumn<>("订单ID");
        orderIdColumn.setCellValueFactory(new PropertyValueFactory<>("order_id"));

        TableColumn<Order, Integer> customerIdColumn = new TableColumn<>("客户ID");
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));

        TableColumn<Order, String> customerNameColumn = new TableColumn<>("客户名称");
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));

        TableColumn<Order, String> orderDateColumn = new TableColumn<>("订单日期");
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("order_date"));

        TableColumn<Order, Double> totalAmountColumn = new TableColumn<>("总金额");
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("money"));

        tableView.getColumns().addAll(orderIdColumn,customerIdColumn ,customerNameColumn, orderDateColumn, totalAmountColumn);

        // 设置按钮样式
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand; -fx-font-family: Microsoft YaHei";
        String buttonHoverStyle = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand; -fx-font-family: Microsoft YaHei";

        // 创建按钮
        Button addButton = new Button("添加", createImageView("/add_icon_white.png", 18, 18));
        addButton.setStyle(buttonStyle);
        addButton.setOnMouseEntered(e -> addButton.setStyle(buttonHoverStyle));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));
        addButton.setOnAction(event -> addOrder());

        Button updateButton = new Button("修改", createImageView("/update_icon_white.png", 18, 18));
        updateButton.setStyle(buttonStyle);
        updateButton.setOnMouseEntered(e -> updateButton.setStyle(buttonHoverStyle));
        updateButton.setOnMouseExited(e -> updateButton.setStyle(buttonStyle));
        updateButton.setOnAction(event -> updateOrder());

        Button deleteButton = new Button("删除", createImageView("/del_icon_white.png", 18, 18));
        deleteButton.setStyle(buttonStyle);
        deleteButton.setOnMouseEntered(e -> deleteButton.setStyle(buttonHoverStyle));
        deleteButton.setOnMouseExited(e -> deleteButton.setStyle(buttonStyle));
        deleteButton.setOnAction(event -> {
            try {
                deleteOrder();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        Button viewButton = new Button("查看", createImageView("/view_icon_white.png", 18, 18));
        viewButton.setStyle(buttonStyle);
        viewButton.setOnMouseEntered(e -> viewButton.setStyle(buttonHoverStyle));
        viewButton.setOnMouseExited(e -> viewButton.setStyle(buttonStyle));
        viewButton.setOnAction(event -> viewOrder());

        Button searchButton = new Button("搜索", createImageView("/search_icon_white.png", 18, 18));
        searchButton.setStyle(buttonStyle);
        searchButton.setOnMouseEntered(e -> searchButton.setStyle(buttonHoverStyle));
        searchButton.setOnMouseExited(e -> searchButton.setStyle(buttonStyle));
        searchButton.setOnAction(event -> searchOrder());

        HBox buttonBox = new HBox(10, addButton, updateButton, deleteButton, viewButton, searchButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(10));

        refreshTableData();
        // 布局
        VBox layout = new VBox(10, title, tableView, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#DCDCDC"), CornerRadii.EMPTY, Insets.EMPTY)));

        this.getChildren().add(layout);
    }

    // 添加商品
    private void addOrder() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("添加订单");

        Label orderIdLabel = new Label("订单ID:");
        orderIdLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField orderIdField = new TextField();
        orderIdField.setPromptText("请输入订单ID");

        Label customerIdLabel = new Label("客户ID:");
        customerIdLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField customerIdField = new TextField();
        customerIdField.setPromptText("请输入客户ID");

        Label customerNameLabel = new Label("客户名称:");
        customerNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField customerNameField = new TextField();
        customerNameField.setPromptText("请输入客户名称");

        // 创建 Label 和 DatePicker 控件
        Label orderDateLabel = new Label("订单日期:");
        orderDateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        DatePicker orderDatePicker = new DatePicker();
        orderDatePicker.setStyle("-fx-pref-width: 200px;");

        // 创建 VBox 布局并添加控件
        VBox vbox1 = new VBox(10, orderDateLabel, orderDatePicker);
        vbox1.setPadding(new Insets(10));
        vbox1.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 10px;");

        Label totalAmountLabel = new Label("总金额:");
        totalAmountLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField totalAmountField = new TextField();
        totalAmountField.setPromptText("请输入总金额");

        Button addButton = new Button("添加");
        addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-background-color: #45A049; -fx-text-fill: white; -fx-font-size: 14px;"));
        addButton.setOnMouseExited(e -> addButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;"));
        addButton.setOnAction(event -> {
            try {
                AddData.addOrder(Integer.parseInt(orderIdField.getText())
                        ,Integer.parseInt(customerIdField.getText())
                        ,customerNameField.getText()
                        , String.valueOf(orderDatePicker.getValue())
                        ,Double.parseDouble(totalAmountField.getText()));
                MyAlert.alertSet("添加订单","成功","!恭喜您!");
                dialog.close();
                refreshTableData();
            } catch (SQLException e) {
                if (e instanceof MysqlDataTruncation) {
                    // 处理MysqlDataTruncation异常
                    MyAlert.alertSet("添加订单","失败","请输入正确的日期格式！");
                } else {
                    // 处理其他SQLException异常
                    MyAlert.alertSet("添加订单","失败","请勿添加已存在的订单！");

                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                MyAlert.alertSet("添加订单","失败","请输入正确的信息格式！");
            }
        });

        // 设置 DatePicker 的宽度与 TextField 一致
        orderDatePicker.setPrefWidth(500);

        // 创建主 VBox 布局
        VBox vbox = new VBox(15,
            createStyledVBox(orderIdLabel, orderIdField),
            createStyledVBox(customerIdLabel, customerIdField),
            createStyledVBox(customerNameLabel, customerNameField),
            vbox1,
            createStyledVBox(totalAmountLabel, totalAmountField),
            addButton);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 创建并设置 Scene
        Scene scene = new Scene(vbox, 350, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // 创建信息框的方法
    private VBox createStyledVBox(Label label, TextField textField) {
        VBox vbox = new VBox(5, label, textField);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 10px;");
        return vbox;
    }
    private VBox createStyledVBox(Label label, Label value) {
        VBox vbox = new VBox(5, label, value);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        return vbox;
    }

    // 修改订单
    private void updateOrder() {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            MyAlert.alertSet("失败", "请选择要删除的订单", "...");
            return;
        }
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("修改订单");

        Label orderIdLabel = new Label("订单ID:");
        orderIdLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField orderIdField = new TextField(String.valueOf(selectedOrder.getOrder_id()));
        orderIdField.setEditable(false);

        Label customerIdLabel = new Label("客户ID:");
        customerIdLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField customerIdField = new TextField(String.valueOf(selectedOrder.getCustomer_id()));

        Label customerNameLabel = new Label("客户名称:");
        customerNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField customerNameField = new TextField(selectedOrder.getCustomer_name());

        Label orderDateLabel = new Label("订单日期:");
        orderDateLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField orderDateField = new TextField(selectedOrder.getOrder_date());

        // 创建 VBox 布局并添加控件
        VBox vbox1 = new VBox(10, orderDateLabel, orderDateField);
        vbox1.setPadding(new Insets(10));
        vbox1.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 10px;");

        Label totalAmountLabel = new Label("总金额:");
        totalAmountLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        TextField totalAmountField = new TextField(String.valueOf(selectedOrder.getMoney()));

        Button updateButton = new Button("保存");
        updateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        updateButton.setOnAction(event -> {
            // 保存逻辑
            try {
                UpdateData.updateOrder(Integer.parseInt(orderIdField.getText())
                        ,Integer.parseInt(customerIdField.getText())
                        ,customerNameField.getText(),orderDateField.getText()
                        ,Double.parseDouble(totalAmountField.getText()));
                MyAlert.alertSet("修改订单","成功","!恭喜您!");
                refreshTableData();
                dialog.close();
            } catch (SQLException e) {
                MyAlert.alertSet("修改订单","失败","请勿修改已存在的订单");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                MyAlert.alertSet("修改订单","失败","请输入正确的信息格式");
            }
            dialog.close();
        });

        // 创建主 VBox 布局
        VBox vbox = new VBox(15,
                createStyledVBox(orderIdLabel, orderIdField),
                createStyledVBox(customerIdLabel, customerIdField),
                createStyledVBox(customerNameLabel, customerNameField),
                vbox1,
                createStyledVBox(totalAmountLabel, totalAmountField),
                updateButton);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 创建并设置 Scene
        Scene scene = new Scene(vbox, 350, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // 删除订单
    private void deleteOrder() throws SQLException, ClassNotFoundException {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            MyAlert.alertSet("错误", "请选择要删除的订单！", "" );
            return;
        }

        // 创建确认删除的对话框
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("确认删除");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("您确定要删除该订单吗？");

        // 添加按钮
        ButtonType buttonTypeYes = new ButtonType("是", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("否", ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // 处理用户的选择
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            try {
                DeleteData.deleteOrder(selectedOrder.getOrder_id());
                MyAlert.alertSet("删除订单","成功","!恭喜您!");
                refreshTableData();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
                MyAlert.alertSet("错误", "删除订单失败!", "");
            }
        }
    }

    // 查看订单
    private void viewOrder() {
        Order selectedOrder = tableView.getSelectionModel().getSelectedItem();
        if (selectedOrder == null) {
            MyAlert.alertSet("错误", "请选择要查看的订单", "...");
            return;
        }
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("查看订单");

        // 样式设置
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold;";
        String valueStyle = "-fx-font-size: 14px; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px; -fx-padding: 5px;";

        // 创建控件
        Label orderIdLabel = new Label("订单ID: ");
        orderIdLabel.setStyle(labelStyle);
        Label orderIdValue = new Label(String.valueOf(selectedOrder.getOrder_id()));
        orderIdValue.setStyle(valueStyle);

        Label customerNameLabel = new Label("客户名称: ");
        customerNameLabel.setStyle(labelStyle);
        Label customerNameValue = new Label(selectedOrder.getCustomer_name());
        customerNameValue.setStyle(valueStyle);

        Label orderDateLabel = new Label("订单日期: ");
        orderDateLabel.setStyle(labelStyle);
        Label orderDateValue = new Label(selectedOrder.getOrder_date());
        orderDateValue.setStyle(valueStyle);

        Label totalAmountLabel = new Label("总金额: ");
        totalAmountLabel.setStyle(labelStyle);
        Label totalAmountValue = new Label(String.valueOf(selectedOrder.getMoney()));
        totalAmountValue.setStyle(valueStyle);

        // 创建带样式的 VBox 方法
        VBox orderIdBox = createStyledVBox(orderIdLabel, orderIdValue);
        VBox customerNameBox = createStyledVBox(customerNameLabel, customerNameValue);
        VBox orderDateBox = createStyledVBox(orderDateLabel, orderDateValue);
        VBox totalAmountBox = createStyledVBox(totalAmountLabel, totalAmountValue);

        VBox vbox = new VBox(15, orderIdBox, customerNameBox, orderDateBox, totalAmountBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 场景
        Scene scene = new Scene(vbox, 400, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    private void viewOrder(Order order) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("查看订单");

        // 样式设置
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold;";
        String valueStyle = "-fx-font-size: 14px; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px; -fx-padding: 5px;";

        // 创建控件
        Label orderIdLabel = new Label("订单ID: ");
        orderIdLabel.setStyle(labelStyle);
        Label orderIdValue = new Label(String.valueOf(order.getOrder_id()));
        orderIdValue.setStyle(valueStyle);

        Label customerNameLabel = new Label("客户名称: ");
        customerNameLabel.setStyle(labelStyle);
        Label customerNameValue = new Label(order.getCustomer_name());
        customerNameValue.setStyle(valueStyle);

        Label orderDateLabel = new Label("订单日期: ");
        orderDateLabel.setStyle(labelStyle);
        Label orderDateValue = new Label(order.getOrder_date());
        orderDateValue.setStyle(valueStyle);

        Label totalAmountLabel = new Label("总金额: ");
        totalAmountLabel.setStyle(labelStyle);
        Label totalAmountValue = new Label(String.valueOf(order.getMoney()));
        totalAmountValue.setStyle(valueStyle);

        // 创建带样式的 VBox 方法
        VBox orderIdBox = createStyledVBox(orderIdLabel, orderIdValue);
        VBox customerNameBox = createStyledVBox(customerNameLabel, customerNameValue);
        VBox orderDateBox = createStyledVBox(orderDateLabel, orderDateValue);
        VBox totalAmountBox = createStyledVBox(totalAmountLabel, totalAmountValue);

        VBox vbox = new VBox(15, orderIdBox, customerNameBox, orderDateBox, totalAmountBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 场景
        Scene scene = new Scene(vbox, 400, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    // 搜索订单
    private void searchOrder() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("搜索订单");

        Button searchByNameButton = new Button("根据客户ID搜索");
        searchByNameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchByNameButton.setOnMouseEntered(e -> searchByNameButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByNameButton.setOnMouseExited(e -> searchByNameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByNameButton.setOnAction(event -> {
            showInputDialog("客户ID");
            dialog.close();
        });

        Button searchByIdButton = new Button("根据订单ID搜索");
        searchByIdButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchByIdButton.setOnMouseEntered(e -> searchByIdButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByIdButton.setOnMouseExited(e -> searchByIdButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByIdButton.setOnAction(event -> {
            showInputDialog("订单ID");
            dialog.close();
        });

        HBox hbox = new HBox(15, searchByNameButton, searchByIdButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));
        hbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        Scene scene = new Scene(hbox, 400, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    @Override
    public void showInputDialog(String searchType) {
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
        String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("输入" + searchType);

        Label label = new Label("请输入" + searchType + ":");
        label.setStyle(labelStyle);

        // 获得数据文本框
        TextField textField = new TextField();
        textField.setStyle(textFieldStyle);

        Button searchButton = new Button("搜索");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchButton.setOnAction(event -> {
            String input = textField.getText();
            try {
                judgeProductData(searchType, input);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });

        VBox vbox = new VBox(15, label, textField, searchButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        Scene scene = new Scene(vbox, 400, 300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    @Override
    public void judgeProductData(String searchType, String input) throws SQLException, ClassNotFoundException {
        try {
            Order order;
            if (searchType.equals("订单ID")) {
                order = DataToObjects.dataToOrderByOrderId(input);
            } else {
                order = DataToObjects.dataToOrderByCustomerId(input);
            }
            viewOrder(order);
        } catch (NullPointerException e) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("搜索失败");
            alert.setHeaderText(null);
            alert.setContentText("找不到该订单！");
            alert.showAndWait();
        }
    }
    @Override
    public void refreshTableData() {
        try {
            String sql = "SELECT * FROM aorder";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ResultSet resultSet = ppst.executeQuery();
            ObservableList<Order> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Order order = new Order(resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                        resultSet.getString("customer_name"),
                        resultSet.getString("order_date"),
                        resultSet.getDouble("money"));
                list.add(order);
            }
            tableView.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}