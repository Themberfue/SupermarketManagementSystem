package org.marketsystem.blackmarket.loginAndRegister;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.marketsystem.blackmarket.base.BaseLoginAndResgister;
import org.marketsystem.blackmarket.cashierManage.CashierManagementSystem;
import org.marketsystem.blackmarket.customerInfo.commonCustomerInfo.CommonCustomerInfo;
import org.marketsystem.blackmarket.customerInfo.plusCustomerInfo.PlusCustomerInfo;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.Verify;
import org.marketsystem.blackmarket.warehouseManage.WarehouseManagementSystem;

import java.sql.SQLException;

import static javafx.application.Application.launch;

public class login extends BaseLoginAndResgister {
    public static final int WIDTH = 1442;
    public static final int HEIGHT = 800;
    public static boolean loginFlag = false;
    public static String username;
    public static String password;
    protected TextField userTextField;
    protected PasswordField passwordField;
    protected CheckBox rememberMeCheckBox;
    protected Hyperlink forgotPasswordLink;
    protected Button loginButton;
    protected HBox hbBtn;
    protected FadeTransition fadeTransition;
    protected TranslateTransition translateTransition;

    protected void createUI(Stage primaryStage) {
        // 设置窗口标题
        primaryStage.setTitle("欢迎来到黑市登录界面！");
        // 清除现有图标
        primaryStage.getIcons().clear();
        // 设置窗口图标
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/blackmarket_icon.png")));

        // 创建头部区域
        createHeaderComponent();

        // 创建中间上半区域
        createMiddleTopComponent();
        // 创建中间下半区域
        createMiddleBottomComponent();

        // 创建网格布局组件
        createGridComponent(primaryStage);

        // 用户名框回车事件
        userTextField.setOnKeyPressed(KeyEvent -> enterAction(KeyEvent, primaryStage));

        // 密码框回车事件
        passwordField.setOnKeyPressed(KeyEvent -> enterAction(KeyEvent, primaryStage));

        // 超链接点击事件处理
        forgotPasswordLink.setOnAction(event -> hyperLinkClickAction(primaryStage));

        // 登录按钮事件处理
        loginButton.setOnAction(e -> buttonClickAction(primaryStage));

        // 创建左侧图片
        createLeftSideImageComponent();

        // 创建背景
        createBackGroundComponent(primaryStage);

        primaryStage.show();
        primaryStage.centerOnScreen(); // 窗口居中
    }

    private void handleVerify(String username, String password, Stage primaryStage) {
        try {
            if (Verify.verifyManager()) {
                loginFlag = true;
                new WarehouseManagementSystem().start(primaryStage);
            }else if (Verify.verifyCashier()) {
                loginFlag = true;
                new CashierManagementSystem().start(primaryStage);
            }else if(Verify.verifyPlusCustomer()) {
                loginFlag = true;
                new PlusCustomerInfo().start(primaryStage);
                //System.out.println(11111222);
            }else if(Verify.verifyCustomer()){
                loginFlag = true;
                new CommonCustomerInfo().start(primaryStage);
                //System.out.println("1");
            }
            if (!loginFlag){
                // 如果四个验证都失败，显示警告消息
                MyAlert.alertSet("这也能错？","!","账号或密码错误!");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
    protected void createGridComponent(Stage primaryStage) {
        // 创建网格布局
        grid = new GridPane();
        grid.setPadding(new Insets(40, 40, 40, 40)); // 调整边距以适应更大窗口
        grid.setHgap(10); // 增加水平间距
        grid.setVgap(20); // 增加垂直间距
        grid.setAlignment(Pos.CENTER); // 设置网格布局居中

        // 添加头部
        grid.add(titleBox, 0, 0, 2, 1);
        GridPane.setHalignment(titleBox, HPos.CENTER);

        // 添加中间用户名文本框
        grid.add(userNameLabel, 0, 1);
        grid.add(userTextField, 1, 1);

        // 添加中间密码文本框
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);

        grid.add(rememberMeCheckBox, 1, 3);

        grid.add(hbBtn, 1, 5);

        grid.add(hBox, 1, 4);

        // 创建一个 VBox 将 gird 居中
        vbox = new VBox(grid);
        vbox.setMaxWidth(500);
        vbox.setMaxHeight(400);
        vbox.setStyle(
                "-fx-background-color: white;-fx-border-width: 1px; -fx-border-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px;"
        );

        createTraditionComponent();

        // 当舞台显示时启动过渡
        primaryStage.setOnShown(event -> {
            fadeTransition.play();
            translateTransition.play();
        });
    }
    protected void createTraditionComponent() {
        // 创建 FadeTransition
        fadeTransition = new FadeTransition(Duration.millis(2000), grid);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        // 创建 TranslateTransition
        translateTransition = new TranslateTransition(Duration.millis(2000), grid);
        translateTransition.setFromX(-100);
        translateTransition.setToX(0);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
    }
    protected void createHeaderComponent() {
        // 登录标题和图标
        sceneTitle = new Text("登录");
        sceneTitle.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 25));

        ImageView iconView = createImageComponent("/login_icon.png", 30, 30);

        // 将标题和图标放在一起
        titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(iconView, sceneTitle);
    }
    protected void createMiddleTopComponent() {
        createFieldComponent();
        // 复选框
        rememberMeCheckBox = new CheckBox("保持登录状态");
        rememberMeCheckBox.setTextFill(Color.BLACK);
        rememberMeCheckBox.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 13));
        rememberMeCheckBox.setStyle("-fx-cursor: hand");
    }
    protected void createFieldComponent() {
        // 用户名标签
        userNameLabel = new Label("用户名");
        userNameLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        // 用户名文本框
        userTextField = new TextField();
        userTextField.setPrefHeight(40); // 增加文本框高度
        userTextField.setPrefWidth(300); // 增加文本框宽度
        userTextField.setPromptText("请输入用户名");

        // 密码标签
        passwordLabel = new Label("密码");
        passwordLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        // 密码文本框
        passwordField = new PasswordField();
        passwordField.setPrefHeight(40); // 增加密码框高度
        passwordField.setPrefWidth(300); // 增加密码框宽度
        passwordField.setPromptText("请输入密码");
    }
    protected void createMiddleBottomComponent() {
        // 已有账户链接
        forgotPasswordLink = new Hyperlink("还没有账户？");
        forgotPasswordLink.setFont(Font.font("Arial", FontWeight.NORMAL, 15));
        forgotPasswordLink.setStyle("-fx-text-fill: #007FFF; -fx-underline: true;");
        forgotPasswordLink.setOnMouseEntered(e -> forgotPasswordLink.setStyle("-fx-text-fill: #0059B3; -fx-underline: true;"));
        forgotPasswordLink.setOnMouseExited(e -> forgotPasswordLink.setStyle("-fx-text-fill: #007FFF; -fx-underline: true;"));

        createButtonComponent();

        hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT); //按钮右对齐
        hbBtn.getChildren().add(loginButton);

        // 创建一个 HBox 将复选框和按钮并排放置
        hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(forgotPasswordLink, loginButton);
    }
    protected void createButtonComponent() {
        // 登录按钮图标
        ImageView loginIconView = createImageComponent("/login_icon_white.png", 23, 23);
        // 登录按钮
        loginButton = new Button("登录", loginIconView);
        loginButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #007FFF, #0059B3); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"
        );
        loginButton.setOnMouseEntered(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #0059B3, #007FFF); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"
        ));
        loginButton.setOnMouseExited(e -> loginButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #007FFF, #0059B3); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"
        ));
        loginButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 18));
        loginButton.setPrefHeight(40);
        loginButton.setPrefWidth(100);
    }
    protected void hyperLinkClickAction(Stage primaryStage) {
        System.out.println(userTextField.getText() + 1);
        Register register = new Register();
        try {
            register.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void buttonClickAction(Stage primaryStage) {
        username = userTextField.getText();
        password = passwordField.getText();
        handleVerify(username, password, primaryStage);
    }
    private void enterAction(KeyEvent keyEvent, Stage primaryStage) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            username = userTextField.getText();
            password = passwordField.getText();
            handleVerify(username, password, primaryStage);
        }
    }

    public static void main(String[] args) {launch(args);}
}