package org.marketsystem.blackmarket.cashierManage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.marketsystem.blackmarket.dataSheet.Customer;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.MysqlCon;
import org.marketsystem.blackmarket.utils.SearchData;
import org.marketsystem.blackmarket.utils.UpdateData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @className: CustomerInfoView
 * @author: 朝槿
 * @date: 2024/6/20 10:14
 */
public class CustomerInfoView extends StackPane {
    private TableView<Customer> tableView;

    public CustomerInfoView() throws SQLException, ClassNotFoundException {
        // 创建标题
        Label title = new Label("客户信息管理");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 30));

        // 创建一个文本输入框用于搜索
        TextField searchField = new TextField();
        searchField.setPromptText("搜索客户Id...");
        searchField.setMinWidth(850);
        searchField.setStyle("-fx-font-size: 13px; -fx-padding: 8px;");

        // 创建搜索按钮
        Button searchButton = new Button("搜索", createImageView("/search_icon_white.png"));
        searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));
        searchButton.setOnAction(e -> {
            try {
                searchCustomer(searchField.getText());
                searchField.setText("");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException | NumberFormatException ex) {
                MyAlert.alertSet("查找客户","失败","！请输入正确的客户Id！");
            }
        });

        // 创建水平盒子用于容纳搜索框和按钮
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(20);
        searchBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-padding: 13px");

        // 创建信息标签
        Label totalCustomersLabel = new Label("总客户数: " + getTotalCustomersCount());
        totalCustomersLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));

        Label plusCustomersLabel = new Label("Plus客户数: " + getPlusCustomersCount());
        plusCustomersLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));
        plusCustomersLabel.setStyle("-fx-text-fill: Red");

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

        // 刷新按钮事件处理
        refreshButton.setOnAction(e -> refreshTableCustomerData());

        // 展示个别信息盒子
        HBox infoBox = new HBox();
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10));
        infoBox.setSpacing(20);
        infoBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px;");
        infoBox.getChildren().addAll(totalCustomersLabel, plusCustomersLabel, refreshButton);

        // 创建客户信息表格
        tableView = new TableView<>();

        tableView.setEditable(true);

        // 创建列
        TableColumn<Customer, Integer> idColumn = new TableColumn<>("客户ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        idColumn.setPrefWidth(150); // 设置列宽

        TableColumn<Customer, String> nameColumn = new TableColumn<>("客户名称");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("customer_name"));
        nameColumn.setPrefWidth(150); // 设置列宽

        TableColumn<Customer, String> phoneColumn = new TableColumn<>("电话");
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        phoneColumn.setPrefWidth(200); // 设置列宽

        TableColumn<Customer, String> emailColumn = new TableColumn<>("电子邮箱");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setPrefWidth(250); // 设置列宽

        TableColumn<Customer, String> addressColumn = new TableColumn<>("地址");
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumn.setPrefWidth(310); // 设置列宽

        TableColumn<Customer, String> isPlusColumn = new TableColumn<>("是否Plus");
        isPlusColumn.setCellValueFactory(new PropertyValueFactory<>("is_plus"));
        isPlusColumn.setPrefWidth(100); // 设置列宽

        tableView.getColumns().addAll(idColumn, nameColumn, phoneColumn, emailColumn, addressColumn, isPlusColumn);

        refreshTableCustomerData();

        // 创建更新按钮
        Button updateButton = new Button("更改客户Plus状态", createImageView("/update_icon_white.png"));
        updateButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));
        updateButton.setStyle(
                "-fx-background-color: #336699; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        );
        updateButton.setOnMouseEntered(e -> updateButton.setStyle(
                "-fx-background-color: #274b73; " +  // 悬停时颜色变化
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));
        updateButton.setOnMouseExited(e -> updateButton.setStyle(
                "-fx-background-color: #336699; " +  // 恢复默认样式
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));

        // 创建查看按钮
        Button viewButton = new Button("查看客户", createImageView("/view_icon_white.png"));
        viewButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));
        viewButton.setStyle(
                "-fx-background-color: #336699; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        );
        viewButton.setOnMouseEntered(e -> viewButton.setStyle(
                "-fx-background-color: #274b73; " +  // 悬停时颜色变化
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));
        viewButton.setOnMouseExited(e -> viewButton.setStyle(
                "-fx-background-color: #336699; " +  // 恢复默认样式
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));

        updateButton.setOnAction(e -> updateCustomer());
        viewButton.setOnAction(e -> viewCustomer());

        HBox buttonBox = new HBox(80, updateButton, viewButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px;");

        VBox layout = new VBox(10, title, searchBox, infoBox, tableView, buttonBox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.getChildren().add(layout);
    }
    // 快捷创建图像
    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }

    // 获取总客户数
    private int getTotalCustomersCount() throws SQLException, ClassNotFoundException {
        // 实现获取总客户数的逻辑
        ;
        return SearchData.NumberOfCustoms();
    }

    // 获取Plus客户数
    private int getPlusCustomersCount() throws SQLException, ClassNotFoundException {
        // 实现获取Plus客户数的逻辑
        return SearchData.NumberOfPlusCustomer();
    }

    // 刷新表格数据

    private void updateCustomer() {
        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("请选择要修改的客户！");
            return;
        }
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("修改客户Plus状态");

        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold;";
        String boxStyle = "-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-padding: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);";

        // 创建标签和对应的 VBox
        Label idLabel = new Label("客户ID: " + selectedCustomer.getCustomer_id());
        idLabel.setStyle(labelStyle);
        VBox idBox = new VBox(idLabel);
        idBox.setStyle(boxStyle);

        Label nameLabel = new Label("客户名称: " + selectedCustomer.getCustomer_name());
        nameLabel.setStyle(labelStyle);
        VBox nameBox = new VBox(nameLabel);
        nameBox.setStyle(boxStyle);

        Label phoneLabel = new Label("电话: " + selectedCustomer.getPhone());
        phoneLabel.setStyle(labelStyle);
        VBox phoneBox = new VBox(phoneLabel);
        phoneBox.setStyle(boxStyle);

        Label emailLabel = new Label("电子邮箱: " + selectedCustomer.getEmail());
        emailLabel.setStyle(labelStyle);
        VBox emailBox = new VBox(emailLabel);
        emailBox.setStyle(boxStyle);

        Label addressLabel = new Label("地址: " + selectedCustomer.getAddress());
        addressLabel.setStyle(labelStyle);
        VBox addressBox = new VBox(addressLabel);
        addressBox.setStyle(boxStyle);

        Label isVipLabel = new Label("是否Plus:");
        isVipLabel.setStyle(labelStyle);
        CheckBox isVipCheckBox = new CheckBox();
        if (selectedCustomer.getIs_plus().equals("是")) {
            selectedCustomer.setIs_vip(true);
        } else {
            selectedCustomer.setIs_vip(false);
        }
        isVipCheckBox.setSelected(selectedCustomer.isIs_vip());
        VBox vipBox = new VBox(10, isVipLabel, isVipCheckBox);
        vipBox.setStyle(boxStyle);

        // 创建保存按钮
        Button updateButton = new Button("保存");
        updateButton.setStyle(
                "-fx-background-color: #336699; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        );
        updateButton.setOnMouseEntered(e -> updateButton.setStyle(
                "-fx-background-color: #274b73; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));
        updateButton.setOnMouseExited(e -> updateButton.setStyle(
                "-fx-background-color: #336699; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-padding: 10px 20px; " +
                        "-fx-background-radius: 5px; " +
                        "-fx-border-radius: 5px; " +
                        "-fx-cursor: hand;"
        ));
        updateButton.setOnAction(event -> {
            selectedCustomer.setIs_vip(isVipCheckBox.isSelected());
            //TODO
            try {
                UpdateData.updateIsVip(selectedCustomer.getCustomer_id(),isVipCheckBox.isSelected());
                refreshTableCustomerData();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            tableView.refresh();
            dialog.close();

        });
        refreshTableCustomerData();
        // 将所有元素放入 VBox
        VBox vbox = new VBox(15, idBox, nameBox, phoneBox, emailBox, addressBox, vipBox, updateButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setStyle(
                "-fx-background-color: #FFFAF0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-color: lightgray; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-padding: 20px; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Microsoft YaHei';"
        );

        Scene scene = new Scene(vbox, 400, 500);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    private void searchCustomer(String searchText) throws SQLException, ClassNotFoundException {
        if (SearchData.SearchCustomerById(Integer.parseInt(searchText),tableView)) {
            refreshTableCustomerData(searchText);
        } else {
            MyAlert.alertSet("查找客户", "失败", "！请输入正确的客户Id！");
        }
    }
    private void refreshTableCustomerData(String searchText) {
        try {
            String sql = "SELECT * FROM customer where customer_id = ?";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ppst.setInt(1,Integer.parseInt(searchText));
            ResultSet resultSet = ppst.executeQuery();
            ObservableList<Customer> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Boolean flag = resultSet.getBoolean("is_vip");
                String isPlus = flag ? "是" : "否";
                System.out.println(isPlus);
                Customer customer = new Customer(resultSet.getInt("customer_id")
                        ,resultSet.getString("customer_name")
                        ,resultSet.getString("phone")
                        ,resultSet.getString("email")
                        ,resultSet.getString("address")
                        ,isPlus);
                list.add(customer);
            }
            MysqlCon.closeCon(conn,ppst,resultSet);
            tableView.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid searchText format - " + searchText);
            e.printStackTrace();
        }

    }

    private void refreshTableCustomerData() {
        try {
            String sql = "SELECT * FROM customer";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ResultSet resultSet = ppst.executeQuery();
            ObservableList<Customer> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Boolean flag = resultSet.getBoolean("is_vip");
                String isPlus = flag ? "是" : "否";
                Customer customer = new Customer(resultSet.getInt("customer_id")
                        ,resultSet.getString("customer_name")
                        ,resultSet.getString("phone")
                        ,resultSet.getString("email")
                        ,resultSet.getString("address")
                        ,isPlus);
                list.add(customer);
            }
            MysqlCon.closeCon(conn,ppst,resultSet);
            tableView.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void viewCustomer() {
        Customer selectedCustomer = tableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("请选择要查看的客户！");
            return;
        }
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("查看客户信息");

        // 样式设置
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold;";
        String valueStyle = "-fx-font-size: 14px; -fx-background-color: #e0f7fa; -fx-border-color: #4caf50; -fx-border-radius: 5px; -fx-padding: 5px;";

        Label idLabel = new Label("客户ID: ");
        idLabel.setStyle(labelStyle);
        Label idLabelValue = new Label(String.valueOf(selectedCustomer.getCustomer_id()));
        idLabelValue.setStyle(valueStyle);

        Label nameLabel = new Label("客户名称: ");
        nameLabel.setStyle(labelStyle);
        Label nameLabelValue = new Label(selectedCustomer.getCustomer_name());
        nameLabelValue.setStyle(valueStyle);

        Label phoneLabel = new Label("电话: ");
        phoneLabel.setStyle(labelStyle);
        Label phoneLabelValue = new Label(selectedCustomer.getPhone());
        phoneLabelValue.setStyle(valueStyle);

        Label emailLabel = new Label("电子邮箱: ");
        emailLabel.setStyle(labelStyle);
        Label emailLabelValue = new Label(selectedCustomer.getEmail());
        emailLabelValue.setStyle(valueStyle);

        Label addressLabel = new Label("地址: ");
        addressLabel.setStyle(labelStyle);
        Label addressLabelValue = new Label(selectedCustomer.getAddress());
        addressLabelValue.setStyle(valueStyle);

        Label isVipLabel = new Label("是否VIP: ");
        isVipLabel.setStyle(labelStyle);
        Label isVipLabelValue = new Label(selectedCustomer.getIs_plus());
        isVipLabelValue.setStyle(valueStyle);


        VBox idBox = createStyledVBox(idLabel, idLabelValue);
        VBox customerNameBox = createStyledVBox(nameLabel, nameLabelValue);
        VBox phoneBox = createStyledVBox(phoneLabel, phoneLabelValue);
        VBox emailBox = createStyledVBox(emailLabel, emailLabelValue);
        VBox addressBox = createStyledVBox(addressLabel, addressLabelValue);
        VBox isVipBox = createStyledVBox(isVipLabel, isVipLabelValue);

        VBox vbox = new VBox(10, idBox, customerNameBox, phoneBox, emailBox, addressBox, isVipBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));
        vbox.setStyle(
                "-fx-background-color: #FFFAF0; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-color: lightgray; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-padding: 20px; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Microsoft YaHei';"
        );
        Scene scene = new Scene(vbox, 400, 600);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    // 创建带样式的 VBox 方法
    private VBox createStyledVBox(Label label, Label value) {
        VBox vbox = new VBox(5, label, value);
        vbox.setPadding(new Insets(10));
        vbox.setStyle("-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        return vbox;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("警告");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
