package org.marketsystem.blackmarket.warehouseManage;

import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.marketsystem.blackmarket.base.Basewareandcash;
import org.marketsystem.blackmarket.loginAndRegister.login;

import java.sql.SQLException;

import static org.marketsystem.blackmarket.loginAndRegister.login.*;

public class WarehouseManagementSystem extends Basewareandcash {
    private HostServices hostServices = getHostServices();
    private String warehouseUsername = username;
    private StackPane titlePane;
    private ToolBar navBar;
    private StackPane contentPane;
    public StackPane footerPane;

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        //设置窗口标题
        primaryStage.setTitle("仓库管理系统");
        //清除现有图标
        primaryStage.getIcons().clear();
        //设置窗口图标
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/warehouse.png")));

        // 创建顶部标题区域
        createHeaderComponent();

        // 创建导航栏按钮
        createNavigtionComponent(primaryStage);

        // 创建页脚区域
        createFooterComponent();

        // 创建主布局
        BorderPane mainLayout = new BorderPane();
        VBox topContainer = new VBox(titlePane, navBar);
        mainLayout.setTop(topContainer);
        mainLayout.setCenter(contentPane);
        mainLayout.setBottom(footerPane);

        // 设置主布局的填充和间距
        mainLayout.setPadding(new Insets(0));

        // 创建场景
        Scene scene = new Scene(mainLayout, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createHeaderComponent(){
        Label titleLabel = new Label("仓库管理系统");
        titleLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 40));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.setPadding(new Insets(20)); // 设置背景颜色为绿色

        Image warehouseManageIconImage = new Image(getClass().getResourceAsStream("/warehouse_manage.png"));
        ImageView  warehouseManageIconView = new ImageView( warehouseManageIconImage);
        warehouseManageIconView.setFitWidth(50);
        warehouseManageIconView.setFitHeight(50);
        titleLabel.setGraphic( warehouseManageIconView);

        titlePane = new StackPane(titleLabel);
        titlePane.setStyle("-fx-background-color: #4CAF50;");
        titlePane.setPrefHeight(100);
    }
    private void createNavigtionComponent(Stage primaryStage){
        Button dashboardButton = createNavButton("仪表盘", "/dashboard_icon.png");
        Button inventoryButton = createNavButton("库存管理", "/inventory_icon.png");
        Button ordersButton = createNavButton("订单管理", "/orders_icon.png");
        Button notificationsButton = createNavButton("通知和警报", "/notifications_icon.png");
        Button profileButton = createNavButton("公司成员资料", "/profile_icon.png");
        Button logoutButton = createNavButton("退出登录", "/logout_icon.png");

        // 设置导航按钮样式
        String defaultNavButtonStyle = "-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-cursor: hand;";
        String selectedNavButtonStyle = "-fx-background-color: #111111; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-cursor: hand;";

        dashboardButton.setStyle(defaultNavButtonStyle);
        inventoryButton.setStyle(selectedNavButtonStyle);
        ordersButton.setStyle(defaultNavButtonStyle);
        notificationsButton.setStyle(defaultNavButtonStyle);
        profileButton.setStyle(defaultNavButtonStyle);
        logoutButton.setStyle(defaultNavButtonStyle);

        new InventoryManageView();
        Label userlabel;

        if (!loginFlag) {
            userlabel = new Label("                                                                                                  登录  |  注册");
            userlabel.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-font-family: Microsoft YaHei");
        } else {
            userlabel = new Label("                                                                                          您好！"+warehouseUsername);
            userlabel.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-font-family: Microsoft YaHei");
        }

        HBox hBox = new HBox(userlabel);

        // 创建导航工具栏
        navBar = new ToolBar(
                dashboardButton,
                inventoryButton,
                ordersButton,
                notificationsButton,
                profileButton,
                logoutButton,
                hBox
        );
        navBar.setStyle("-fx-background-color: #333333;");

        // 设置导航栏按钮的最大宽度
        dashboardButton.setMaxWidth(Double.MAX_VALUE);
        inventoryButton.setMaxWidth(Double.MAX_VALUE);
        ordersButton.setMaxWidth(Double.MAX_VALUE);
        notificationsButton.setMaxWidth(Double.MAX_VALUE);
        profileButton.setMaxWidth(Double.MAX_VALUE);
        logoutButton.setMaxWidth(Double.MAX_VALUE);

        // 创建中心内容区域
        contentPane = new StackPane();
        contentPane.getChildren().add(new InventoryManageView()); // 设置初始内容为库存管理视图

        // 添加按钮点击事件处理程序
        addNavButtonClickHandler(dashboardButton, new Button[]{dashboardButton, inventoryButton, ordersButton, notificationsButton, profileButton, logoutButton}, selectedNavButtonStyle, defaultNavButtonStyle, contentPane, new DashboardView());
        addNavButtonClickHandler(inventoryButton, new Button[]{dashboardButton, inventoryButton, ordersButton, notificationsButton, profileButton, logoutButton}, selectedNavButtonStyle, defaultNavButtonStyle, contentPane, new InventoryManageView());
        addNavButtonClickHandler(ordersButton, new Button[]{dashboardButton, inventoryButton, ordersButton, notificationsButton, profileButton, logoutButton}, selectedNavButtonStyle, defaultNavButtonStyle, contentPane, new OrdersManageView());
        addNavButtonClickHandler(notificationsButton, new Button[]{dashboardButton, inventoryButton, ordersButton, notificationsButton, profileButton, logoutButton}, selectedNavButtonStyle, defaultNavButtonStyle, contentPane, new NotificationsView());
        addNavButtonClickHandler(profileButton, new Button[]{dashboardButton, inventoryButton, ordersButton, notificationsButton, profileButton, logoutButton}, selectedNavButtonStyle, defaultNavButtonStyle, contentPane, new ProfileView(hostServices));

        addHoverEffect(logoutButton, defaultNavButtonStyle, selectedNavButtonStyle);
        logoutButton.setOnAction(event -> {
            logoutButton.setStyle(selectedNavButtonStyle);
            new login().start(primaryStage);
            username = null;
        });
    }

    private Button createNavButton(String text, String iconPath) {
        Button button = new Button(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(16);
        iconView.setFitHeight(16);
        button.setGraphic(iconView);
        button.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-font-family: 'Microsoft YaHei UI'");
        return button;
    }

    private void addNavButtonClickHandler(Button button, Button[] allButtons, String selectedStyle, String defaultStyle, StackPane contentPane, Pane content) {
        button.setOnAction(event -> {
            for (Button btn : allButtons) {
                btn.setStyle(defaultStyle);
                // 重新添加悬停和离开事件
                addHoverEffect(btn, defaultStyle, selectedStyle);
            }
            // 移除悬停和离开事件，防止冲突
            button.setOnMouseEntered(null);
            button.setOnMouseExited(null);
            button.setStyle(selectedStyle);
            contentPane.getChildren().clear();
            contentPane.getChildren().add(content);
        });

        // 添加初始悬停和离开事件
        addHoverEffect(button, defaultStyle, selectedStyle);
    }

    // 添加鼠标悬停和离开事件的辅助方法
    private void addHoverEffect(Button button, String defaultStyle, String selectedStyle) {
        button.setOnMouseEntered(e -> {
            if (!button.getStyle().equals(selectedStyle)) {
                button.setStyle("-fx-background-color: #1C1C1C; -fx-text-fill: white; -fx-padding: 20; -fx-font-size: 16px; -fx-cursor: hand;");
            }
        });
        button.setOnMouseExited(e -> {
            if (!button.getStyle().equals(selectedStyle)) {
                button.setStyle(defaultStyle);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}