package org.marketsystem.blackmarket.customerInfo.commonCustomerInfo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.marketsystem.blackmarket.dataSheet.Customer;
import org.marketsystem.blackmarket.loginAndRegister.Register;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.SearchData;
import org.marketsystem.blackmarket.utils.UpdateData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.marketsystem.blackmarket.loginAndRegister.login.username;

public class CommonPersonalInfoView extends Pane {
    public CommonPersonalInfoView() throws SQLException, ClassNotFoundException {
        List<Customer> list = new ArrayList<>();
        if (username != null) {
            list = SearchData.setTableCustomerDataView(username);
        } else {
            list = SearchData.setTableCustomerDataView(Register.userTextField.getText());
        }
        Customer customer = list.get(0);
        Label label = new Label("客户信息");
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 50));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(15);
        grid.setVgap(15);
        grid.setAlignment(Pos.CENTER);

        // ID
        Label idLabel = new Label("ID:");
        idLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        Label customerId = new Label(String.valueOf(customer.getCustomer_id()));
        customerId.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        grid.add(idLabel, 0, 0);
        grid.add(customerId, 1, 0);

        // 昵称
        Label nicknameLabel = new Label("昵称:");
        nicknameLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        TextField nicknameField = new TextField(customer.getCustomer_name());
        nicknameField.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        nicknameField.setPrefWidth(300);
        grid.add(nicknameLabel, 0, 1);
        grid.add(nicknameField, 1, 1, 2, 1);

        // 地址
        Label cityLabel = new Label("城市:");
        cityLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        ComboBox<String> provinceBox = new ComboBox<>();
        provinceBox.setPromptText("选择省份");
        provinceBox.setPrefWidth(300);
        provinceBox.setPromptText(customer.getAddress());
        provinceBox.setStyle("-fx-font-size: 20px;");
        provinceBox.getItems().addAll("北京", "上海", "天津", "重庆",
                "河北", "山西", "辽宁", "吉林", "黑龙江", "江苏", "浙江", "安徽",
                "江西", "山东", "河南", "湖北", "湖南", "福建", "云南", "陕西",
                "广东", "海南", "四川", "甘肃", "青海", "台湾", "宁夏", "新疆", "香港", "澳门");
        HBox cityBoxContainer = new HBox(10, provinceBox);
        grid.add(cityLabel, 0, 2);
        grid.add(cityBoxContainer, 1, 2, 2, 1);

        // 性别选

        // 邮箱
        Label emailLabel = new Label("邮箱:");
        emailLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        TextField emailTextField = new TextField(customer.getEmail());
        emailTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        emailTextField.setPrefWidth(300);
        grid.add(emailLabel, 0, 4);
        grid.add(emailTextField, 1, 4, 2, 1);

        // 电话
        Label phoneLabel = new Label("电话:");
        phoneLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        TextField phoneTextField = new TextField(customer.getPhone());
        phoneTextField.setFont(Font.font("Verdana", FontWeight.NORMAL, 20));
        phoneTextField.setPrefWidth(300);
        grid.add(phoneLabel, 0, 5);
        grid.add(phoneTextField, 1, 5, 2, 1);

        // 保存按钮
        String defaultStyle = "-fx-background-color: #9135dc; -fx-text-fill: white; -fx-padding: 10 20; -fx-cursor: hand; -fx-font-size: 20px;";
        Button saveButton = new Button("保存");
        saveButton.setStyle(defaultStyle);
        saveButton.setOnMouseEntered(e -> {
            saveButton.setStyle("-fx-background-color: #9400D3; -fx-text-fill: white; -fx-padding: 10 20; -fx-cursor: hand; -fx-font-size: 20px;");
        });
        saveButton.setOnMouseExited(e -> {
            saveButton.setStyle(defaultStyle);
        });
        saveButton.setOnAction(e->{
            try {
                UpdateData.updateCustomerInfo(String.valueOf(customer.getCustomer_id())
                        ,nicknameField.getText()
                        ,provinceBox.getValue()
                        ,emailTextField.getText()
                        ,phoneTextField.getText());
                MyAlert.alertSet("保存","成功","！恭喜您！");
            } catch (SQLException ex) {
                MyAlert.alertSet("保存失败","!","请输入改变的信息！");
            } catch (ClassNotFoundException ex) {
                MyAlert.alertSet("保存失败","!","请输入正确格式的信息！");
            }
        });

        HBox saveButtonBox = new HBox(saveButton);
        saveButtonBox.setAlignment(Pos.CENTER);
        grid.add(saveButtonBox, 1, 7, 2, 1);

        // 使用AnchorPane确保VBox居中对齐
        AnchorPane anchorPane = new AnchorPane(grid);
        AnchorPane.setTopAnchor(grid, 100.0);
        AnchorPane.setBottomAnchor(grid, 0.0);
        AnchorPane.setLeftAnchor(grid, 330.0);
        AnchorPane.setRightAnchor(grid, 0.0);
        anchorPane.setPrefSize(1000, 0);

        this.getChildren().add(anchorPane);
    }
}
