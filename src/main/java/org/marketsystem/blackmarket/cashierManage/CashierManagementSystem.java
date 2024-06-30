package org.marketsystem.blackmarket.cashierManage;

/**
 * @className: CashierManagementSystem
 * @author: 朝槿
 * @date: 2024/6/20 8:44
 */

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
import javafx.stage.Stage;
import org.marketsystem.blackmarket.base.Basewareandcash;
import org.marketsystem.blackmarket.loginAndRegister.login;

import java.sql.SQLException;

import static org.marketsystem.blackmarket.loginAndRegister.login.*;

public class CashierManagementSystem extends Basewareandcash {
    private String cashierUsername = username;
    private BorderPane mainLayout;

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        //设置窗口标题
        primaryStage.setTitle("收银员系统");
        //清除现有图标
        primaryStage.getIcons().clear();
        //设置窗口图标
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/getMoney.png")));

        // 创建主布局
        mainLayout = new BorderPane();

        //顶部区域
        createHeaderComponent(primaryStage);

        //左侧菜单栏
        createLeftComponent(primaryStage);

        // 创建页脚
        createFooterComponent();

        // 设置场景
        Scene scene = new Scene(mainLayout, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void createHeaderComponent(Stage primaryStage){
        //收银员系统和退出按钮
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setSpacing(980);
        topBar.setStyle("-fx-background-color: #336699;");
        //收银员
        Label titleLabel = new Label("收银员系统");
        titleLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));
        titleLabel.setTextFill(Color.WHITE);
        //图标
        Image cashierIconImage = new Image(getClass().getResourceAsStream("/cashier.png"));
        ImageView cashierIconView = new ImageView(cashierIconImage);
        cashierIconView.setFitWidth(25);
        cashierIconView.setFitHeight(25);
        titleLabel.setGraphic(cashierIconView);
        // 退出登录按钮
        Button logoutButton = new Button("退出登录");
        logoutButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white; -fx-cursor: hand;");
        logoutButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));

        // 悬浮样式
        String hoverStyle = "-fx-background-color: #FF4500; -fx-text-fill: white; -fx-cursor: hand;";

        // 添加鼠标悬停和点击事件
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle(hoverStyle));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #FF6347; -fx-text-fill: white; -fx-cursor: hand;"));
        logoutButton.setOnAction(e -> {
            new login().start(primaryStage);
            username = null;
        });
        //图标
        Image logoutIconImage = new Image(getClass().getResourceAsStream("/logout_icon.png"));
        ImageView logoutIconView = new ImageView(logoutIconImage);
        logoutIconView.setFitWidth(18);
        logoutIconView.setFitHeight(18);
        logoutButton.setGraphic(logoutIconView);

        Label userlabel;
        if (!loginFlag) {
            userlabel = new Label("登录  |  注册");
            userlabel.setStyle("-fx-text-fill: white;-fx-font-size: 16px; -fx-padding: 5px");
        } else {
            userlabel = new Label("您好！"+cashierUsername);
            userlabel.setStyle("-fx-text-fill: white;-fx-font-size: 16px; -fx-padding: 5px");
        }

        //放置
        HBox tophbox = new HBox();
        tophbox.setAlignment(Pos.CENTER_LEFT);
        tophbox.getChildren().addAll(titleLabel, logoutButton);
        tophbox.setSpacing(10);

        HBox topHBox = new HBox();
        topHBox.setPadding(new Insets(10));
        topHBox.getChildren().addAll(tophbox, userlabel);
        mainLayout.setTop(topHBox);

        topBar.getChildren().addAll(tophbox, topHBox);
        mainLayout.setTop(topBar);
    }
    private void createLeftComponent(Stage primaryStage) throws SQLException, ClassNotFoundException {
        //左侧侧边栏
        VBox leftMenu = new VBox();
        leftMenu.setPadding(new Insets(20));
        leftMenu.setSpacing(20);
        leftMenu.setStyle("-fx-background-color: #f4f4f4;");

        //创建style
        String defaultLeftSideButton = "-fx-background-color: #DCDCDC; -fx-text-fill: #696969; -fx-padding: 20; -fx-cursor: hand";
        String selectedLeftSideButton = "-fx-background-color: #696969; -fx-text-fill: white; -fx-padding: 20; -fx-cursor: hand";

        //按钮
        Button customerInfoButton = createLeftSideButton("客户信息管理", "/customer_info.png");
        Button customerOrderButton = createLeftSideButton("客户订单查询", "/customer_order.png");
        Button salesProcessingButton = createLeftSideButton("销售处理", "/sales_processing.png");
        Button afterSalesServiceButton = createLeftSideButton("售后服务", "/after-sales_service.png");

        customerInfoButton.setStyle(selectedLeftSideButton);
        customerOrderButton.setStyle(defaultLeftSideButton);
        salesProcessingButton.setStyle(defaultLeftSideButton);
        afterSalesServiceButton.setStyle(defaultLeftSideButton);

        //放置
        leftMenu.getChildren().addAll(customerInfoButton, customerOrderButton, salesProcessingButton, afterSalesServiceButton);
        mainLayout.setLeft(leftMenu);

        // 创建中央区域
        VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setPadding(new Insets(20));
        centerLayout.setStyle("-fx-background-color: #ffffff;");
        centerLayout.getChildren().add(new CustomerInfoView());
        mainLayout.setCenter(centerLayout);

        //点击改变按钮
        addLeftSideButtonClick(customerInfoButton, new Button[]{customerInfoButton, customerOrderButton, salesProcessingButton, afterSalesServiceButton}, selectedLeftSideButton, defaultLeftSideButton, centerLayout, new CustomerInfoView());
        addLeftSideButtonClick(customerOrderButton, new Button[]{customerInfoButton, customerOrderButton, salesProcessingButton, afterSalesServiceButton}, selectedLeftSideButton, defaultLeftSideButton, centerLayout, new CustomerOrderView());
        addLeftSideButtonClick(salesProcessingButton, new Button[]{customerInfoButton, customerOrderButton, salesProcessingButton, afterSalesServiceButton}, selectedLeftSideButton, defaultLeftSideButton, centerLayout, new SalesProcessingView());
        addLeftSideButtonClick(afterSalesServiceButton, new Button[]{customerInfoButton, customerOrderButton, salesProcessingButton, afterSalesServiceButton}, selectedLeftSideButton, defaultLeftSideButton, centerLayout, new AfterSalesServiceView(primaryStage));
    }

    // 点击改变按钮
    private void addLeftSideButtonClick(Button button, Button[] allButtons, String selectedStyle, String defaultStyle, VBox centerLayout, Pane content) {
        button.setOnAction(e -> {
            for (Button btn : allButtons) {
                btn.setStyle(defaultStyle);
            }
            button.setStyle(selectedStyle);
            centerLayout.getChildren().clear();
            centerLayout.getChildren().add(content);
        });

        // 添加鼠标悬停和离开事件的辅助方法
        button.setOnMouseEntered(e -> {
            if (!button.getStyle().equals(selectedStyle)) {
                button.setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: white; -fx-padding: 20; -fx-cursor: hand;");
            }
        });
        button.setOnMouseExited(e -> {
            if (!button.getStyle().equals(selectedStyle)) {
                button.setStyle(defaultStyle);
            }
        });
    }

    //边栏按钮图标
    private Button createLeftSideButton(String text, String iconPath) {
        Button button = new Button(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(20);
        iconView.setFitHeight(20);
        button.setGraphic(iconView);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 16));
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }
}