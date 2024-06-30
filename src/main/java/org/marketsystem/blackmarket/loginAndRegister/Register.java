package org.marketsystem.blackmarket.loginAndRegister;

/**
 * @ClassDescription:
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/19 14:52
 */

import javafx.animation.PauseTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.marketsystem.blackmarket.base.BaseLoginAndResgister;
import org.marketsystem.blackmarket.customerInfo.commonCustomerInfo.CommonCustomerInfo;
import org.marketsystem.blackmarket.customerInfo.plusCustomerInfo.PlusCustomerInfo;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.AddData;
import java.sql.SQLException;

import static org.marketsystem.blackmarket.loginAndRegister.login.*;

public class Register extends BaseLoginAndResgister {

    public static TextField userTextField = new TextField();
    public static PasswordField passwordField = new PasswordField();
    protected Label confirmPasswordLabel;
    protected PasswordField confirmPasswordField;
    protected CheckBox agreeTermsCheckBox;
    protected CheckBox agreeVipCheckBox;
    protected VBox checkBoxVBox;
    protected Button registerButton;
    protected Hyperlink haveAccountLink;

    @Override
    protected void createUI(Stage primaryStage) {
        // 设置标题
        primaryStage.setTitle("欢迎来到黑市注册界面！");
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

        // 注册按钮事件处理
        registerButton.setOnAction(e -> buttonClickAction(primaryStage));

        // 添加超链接点击事件处理器
        haveAccountLink.setOnAction(event -> hyperLinkClickAction(primaryStage));

        // 创建网格布局组件
        createGridComponent(primaryStage);

        // 创建左侧图片
        createLeftSideImageComponent();

        // 创建背景
        createBackGroundComponent(primaryStage);

        primaryStage.show();
        primaryStage.centerOnScreen(); // 窗口居中
    }

    //判断注册异常
    private boolean validateRegistration(String username, String password, String confirmPassword, boolean termsAccepted) {
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || !termsAccepted) {
            return false;
        }
        return password.equals(confirmPassword);
    }

    //支付页面
    private void showPayPage(Stage primaryStage) {
        // 创建布局
        BorderPane homePage = new BorderPane();

        primaryStage.setTitle("欢迎来到Plus用户支付界面！");

        // 创建标题标签
        Label label = new Label("  尊贵的黑市Plus用户请支付！（每月 $20 美元）");
        label.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 35));
        label.setStyle("-fx-background-color: rgba(255, 255, 255, 0); -fx-text-fill: red; -fx-border-color: red; -fx-border-width: 1px; -fx-border-radius: 10px; -fx-background-radius: 10px;");

        // 收款码
        Image payImage1 = new Image(getClass().getResource("/payZYM.jpg").toExternalForm());
        Image payImage2 = new Image(getClass().getResource("/payLLC.jpg").toExternalForm());
        Image payImage3 = new Image(getClass().getResource("/payLJH.jpg").toExternalForm());
        // 收款码大小
        ImageView imageView1 = new ImageView(payImage1);
        imageView1.setFitWidth(380);
        imageView1.setFitHeight(518);
        imageView1.setPreserveRatio(true);

        ImageView imageView2 = new ImageView(payImage2);
        imageView2.setFitWidth(380);
        imageView2.setFitHeight(518);
        imageView2.setPreserveRatio(true);

        ImageView imageView3 = new ImageView(payImage3);
        imageView3.setFitWidth(380);
        imageView3.setFitHeight(518);
        imageView3.setPreserveRatio(true);

        //按钮
        Button payButton = new Button("支付完成");
        payButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        payButton.setOnMouseEntered(e -> payButton.setStyle("-fx-background-color: #45A049; -fx-text-fill: white; -fx-cursor: hand;"));
        payButton.setOnMouseExited(e -> payButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;"));
        payButton.setOnAction(e -> {
            loginFlag = true;
            String registerUsername = userTextField.getText();
            String registerPassword = passwordField.getText();
            try {
                AddData.addPlusCustomer(Integer.parseInt(registerUsername),registerPassword);
                new PlusCustomerInfo().start(primaryStage);
            } catch (SQLException ex) {
                //System.out.println("ooo");
                MyAlert.alertSet("注册用户","失败！","该Plus用户Id已被注册！");
            } catch (ClassNotFoundException | NumberFormatException ex) {
                //ex.printStackTrace();
                MyAlert.alertSet("注册用户","失败！","请输入正确格式的用户id（只允许整数！）");
            }

        });

        Button cancelButton = new Button("不想支付了？");
        cancelButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-cursor: hand;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;"));
        cancelButton.setOnAction(e -> {
            freeView(primaryStage);
        });
        //放置
        HBox imageHbox = new HBox(10);
        imageHbox.setAlignment(Pos.CENTER);
        imageHbox.getChildren().addAll(imageView1, imageView2, imageView3);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.getChildren().addAll(label, imageHbox);

        HBox buttonBox = new HBox(100);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(payButton, cancelButton);
        buttonBox.setPadding(new Insets(20, 0, 20, 0)); // 添加一些内边距

        // 设置布局
        homePage.setCenter(centerBox);
        homePage.setBottom(buttonBox);

        // 创建背景图像
        Image backgroundImage = new Image(getClass().getResource("/backgr.png").toExternalForm());
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        homePage.setBackground(new Background(background));

        // 设置新场景
        Scene homeScene = new Scene(homePage, WIDTH, HEIGHT);
        primaryStage.setScene(homeScene);
        primaryStage.show();
    }
    //白嫖页面
    private void freeView(Stage primaryStage) {
        //设置舞台
        Stage freeStage = new Stage();
        //设置窗口图标
        freeStage.getIcons().clear();// 清除现有图标
        freeStage.getIcons().add(new Image(getClass().getResourceAsStream("/payPlus.png")));
        freeStage.setTitle("白嫖页面");

        //按钮
        Button freeButton = new Button("我要白嫖！");
        Button cancelButton = new Button("我要继续支付");

        freeButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 25));
        freeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;");
        freeButton.setOnMouseEntered(e -> freeButton.setStyle("-fx-background-color: #45A049; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 10;"));
        freeButton.setOnMouseExited(e -> freeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-background-radius: 10;"));

        cancelButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 25));
        cancelButton.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; -fx-background-radius: 10;");
        cancelButton.setOnMouseEntered(e -> cancelButton.setStyle("-fx-background-color: #e53935; -fx-text-fill: white; -fx-cursor: hand; -fx-background-radius: 10;"));
        cancelButton.setOnMouseExited(e -> cancelButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-background-radius: 10;"));

        //按钮触发事件
        freeButton.setOnAction(e -> enterFreeView(primaryStage, freeStage));
        cancelButton.setOnAction(e -> freeStage.close());
        //放置
        HBox hbox = new HBox(20);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(freeButton, cancelButton);

        Scene scene = new Scene(hbox, 500, 300);
        freeStage.setScene(scene);
        freeStage.show();
    }

    //输入白嫖页面
    private void enterFreeView(Stage primaryStage, Stage freeStage) {
        //设置舞台
        Stage stage = new Stage();
        //设置窗口图标
        stage.getIcons().clear();// 清除现有图标
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/payPlus.png")));
        stage.setTitle("白嫖页面");

        //控件
        Label label1 = new Label("请输入\n");
        label1.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        Label label2 = new Label("我是白嫖怪");
        label2.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 50));
        label2.setTextFill(Color.DARKBLUE);

        TextField textField = new TextField();
        textField.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        textField.setStyle("-fx-background-radius: 10; -fx-border-color: #0000FF; -fx-border-radius: 10;");

        Button checkButton = new Button("确定");
        checkButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 18));
        checkButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 10;");

        // 悬浮样式
        String hoverStyle1 = "-fx-background-color: #1976D2; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand";

        // 添加悬浮事件
        checkButton.setOnMouseEntered(e -> checkButton.setStyle(hoverStyle1));
        checkButton.setOnMouseExited(e -> checkButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand"));

        //按钮触发事件
        checkButton.setOnAction(e -> {
            String test = textField.getText();
            if ("我是白嫖怪".equals(test)) {
                Stage newStage = new Stage();
                Label newLabel = new Label("白嫖成功\uD83D\uDE2D，请稍后...");
                newLabel.setAlignment(Pos.CENTER);
                newLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 30));
                Scene newScene = new Scene(newLabel, 500, 300);
                newStage.setScene(newScene);
                newStage.show();
                //设置滞留时间
                PauseTransition delay = new PauseTransition(Duration.seconds(2));
                delay.setOnFinished(event -> {
                    //关闭页面
                    newStage.close();
                    stage.close();
                    freeStage.close();
                    new Register().start(primaryStage);//跳转登录
                });
                delay.play();//执行
            } else {
                //舞台
                Stage newStage = new Stage();
                newStage.setTitle("不是，哥们");

                //设置窗口图标
                newStage.getIcons().clear();// 清除现有图标
                newStage.getIcons().add(new Image(getClass().getResourceAsStream("/prawn_icon.png")));

                //控件
                Image image = new Image(getClass().getResourceAsStream("/prawn_icon.png"));
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(162);
                imageView.setFitHeight(95);

                Label label = new Label("这能输错？不想白嫖了是吧！");
                label.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 23));
                label.setTextFill(Color.RED);

                Button button = new Button("退出");
                button.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
                button.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand");

                // 悬浮样式
                String hoverStyle = "-fx-background-color: #E64A19; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand";

                // 添加悬浮事件
                button.setOnMouseEntered(event -> button.setStyle(hoverStyle));
                button.setOnMouseExited(event -> button.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand"));

                //按钮触发事件
                button.setOnAction(event -> newStage.close());
                //放置
                HBox hbox = new HBox(10);
                hbox.setAlignment(Pos.CENTER);
                hbox.getChildren().addAll(imageView, label);

                VBox vbox = new VBox(hbox, button);
                vbox.setAlignment(Pos.CENTER);
                vbox.setSpacing(20);

                Scene scene = new Scene(vbox, 500, 300);
                newStage.setScene(scene);
                newStage.show();
            }
        });
        //放置
        VBox labelVBox = new VBox(10);
        labelVBox.setAlignment(Pos.CENTER);
        labelVBox.getChildren().addAll(label1, label2);

        HBox inputHBox = new HBox(10);
        inputHBox.setAlignment(Pos.CENTER);
        inputHBox.getChildren().addAll(textField, checkButton);

        VBox vbox = new VBox(40);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(labelVBox, inputHBox);

        Scene scene = new Scene(vbox, 500, 300);
        stage.setScene(scene);
        stage.show();
    }
    @Override
    protected void createGridComponent(Stage primaryStage) {
        // 创建网格布局
        grid = new GridPane();
        grid.setPadding(new Insets(40, 40, 40, 40)); //调整边距以适应更大窗口
        grid.setHgap(20); // 增加水平间距
        grid.setVgap(15); // 增加垂直间距
        grid.setAlignment(Pos.CENTER); // 设置网格布局居中

        grid.add(titleBox, 0, 0, 2, 1);
        GridPane.setHalignment(titleBox, HPos.CENTER);

        grid.add(userNameLabel, 0, 1);
        grid.add(userTextField, 1, 1);

        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);


        grid.add(confirmPasswordLabel, 0, 3);
        grid.add(confirmPasswordField, 1, 3);

        grid.add(hBox, 1, 4);

        grid.add(haveAccountLink, 1, 5);

        // 创建一个 VBox 将 GridPane 居中
        vbox = new VBox(grid);
        vbox.setAlignment(Pos.CENTER);
        vbox.setMaxWidth(550);
        vbox.setMaxHeight(400);
        vbox.setStyle("-fx-background-color: white;");
        vbox.setStyle(
                "-fx-background-color: white;-fx-border-width: 1px; -fx-border-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px;"
        );
    }
    @Override
    protected void createHeaderComponent() {
        // 注册标题和图标
        sceneTitle = new Text("注册（仅支持注册用户）");
        sceneTitle.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 25)); // 增加字体大小

        // 加载图标
        ImageView iconView = createImageComponent("/register-icon.png", 30, 30);

        // 将标题和图标放在一起
        titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(iconView, sceneTitle);
    }
    @Override
    protected void createMiddleTopComponent() {
        createFieldComponent();

        // 同意复选框
        agreeTermsCheckBox = new CheckBox("同意服务条款");
        agreeTermsCheckBox.setTextFill(Color.BLACK);
        agreeTermsCheckBox.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 13)); // 增加字体大小
        agreeTermsCheckBox.setStyle("-fx-cursor: hand");

        // 注册为VIP复选框
        agreeVipCheckBox = new CheckBox("成为黑市Plus用户");
        agreeVipCheckBox.setTextFill(Color.ORANGERED);
        agreeVipCheckBox.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 13));
        agreeVipCheckBox.setStyle("-fx-cursor: hand");

        // 创建一个 VBox 将复选框垂直放置
        checkBoxVBox = new VBox(10); // 设置垂直间距为10
        checkBoxVBox.getChildren().addAll(agreeTermsCheckBox, agreeVipCheckBox);
    }
    @Override
    protected void createFieldComponent() {
        // 用户名标签和文本框
        userNameLabel = new Label("用户名");
        userNameLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        userTextField = new TextField();
        userTextField.setPrefHeight(40); // 增加文本框高度
        userTextField.setPrefWidth(300); // 增加文本框宽度
        userTextField.setPromptText("请输入用户名");

        // 密码标签和文本框
        passwordLabel = new Label("密码");
        passwordLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        passwordField = new PasswordField();
        passwordField.setPrefHeight(40); // 增加密码框高度
        passwordField.setPrefWidth(300); // 增加密码框宽度
        passwordField.setPromptText("请输入密码");

        // 确认密码标签和文本框
        confirmPasswordLabel = new Label("确认密码");
        confirmPasswordLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15)); // 增加字体大小
        confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefHeight(40); // 增加密码框高度
        confirmPasswordField.setPrefWidth(300); // 增加密码框宽度
        confirmPasswordField.setPromptText("请再次确认密码");
    }
    @Override
    protected void createMiddleBottomComponent() {
        createButtonComponent();

        // 创建一个 HBox 将复选框VBox和按钮并排放置
        hBox = new HBox(100);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(checkBoxVBox, registerButton);

        // 已有账户链接
        haveAccountLink = new Hyperlink("已有账户？");
        haveAccountLink.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15)); // 更改字体
        haveAccountLink.setStyle("-fx-text-fill: #007FFF; -fx-underline: true;"); // 默认白色和下划线
        haveAccountLink.setOnMouseEntered(e -> haveAccountLink.setStyle("-fx-text-fill: #0059B3; -fx-underline: true;")); // 鼠标悬停样式（浅黄色）
        haveAccountLink.setOnMouseExited(e -> haveAccountLink.setStyle("-fx-text-fill: #007FFF; -fx-underline: true;")); // 鼠标移开样式（白色）
    }
    @Override
    protected void createButtonComponent() {
        // 加载图标
        ImageView resgisterIconView = createImageComponent("/resgister_icon.png", 23, 23);

        // 注册按钮
        registerButton = new Button("注册", resgisterIconView);
        registerButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #007FFF, #0059B3);-fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px;-fx-cursor: hand;"
        ); // 默认样式
        registerButton.setOnMouseEntered(e -> registerButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #0059B3, #007FFF); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"
        ));
        registerButton.setOnMouseExited(e -> registerButton.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #007FFF, #0059B3); -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10px 20px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-cursor: hand;"
        ));
        registerButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 18)); // 增加字体大小
        registerButton.setPrefHeight(40); // 增加按钮高度
        registerButton.setPrefWidth(100); // 增加按钮宽度
    }
    @Override
    protected void hyperLinkClickAction(Stage primaryStage) {
        login login1 = new login();
        try {
            login1.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void buttonClickAction(Stage primaryStage) {
        String registerUsername = userTextField.getText();
        String registerPassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        if (validateRegistration(registerUsername, registerPassword, confirmPassword, agreeTermsCheckBox.isSelected()) && agreeVipCheckBox.isSelected()) {
            showPayPage(primaryStage);
        }
        if ((validateRegistration(registerUsername, registerPassword, confirmPassword, agreeTermsCheckBox.isSelected())) && !(agreeVipCheckBox.isSelected())) {
            loginFlag = true;
            try {
                AddData.addCustomer(Integer.parseInt(registerUsername), registerPassword);
                new CommonCustomerInfo().start(primaryStage);
            } catch (SQLException ex) {
                ex.printStackTrace();
                MyAlert.alertSet("注册用户","失败！","该Id已被注册！");
            } catch (ClassNotFoundException | NumberFormatException ex) {
                ex.printStackTrace();
                MyAlert.alertSet("注册用户","失败！","请输入正确....格式的用户id（只允许整数！）");
            }
        }
        if (!validateRegistration(registerUsername, registerPassword, confirmPassword, agreeTermsCheckBox.isSelected())) {
            MyAlert.alertSet("这能错？（我同意≠我接受）","!","请同意条款！");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}