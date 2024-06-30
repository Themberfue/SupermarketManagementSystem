package org.marketsystem.blackmarket.customerInfo.commonCustomerInfo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.marketsystem.blackmarket.loginAndRegister.Register;
import org.marketsystem.blackmarket.loginAndRegister.login;

import java.sql.SQLException;

import static org.marketsystem.blackmarket.loginAndRegister.Register.userTextField;
import static org.marketsystem.blackmarket.loginAndRegister.login.loginFlag;
import static org.marketsystem.blackmarket.loginAndRegister.login.username;

public class CommonCustomerInfo extends Application {
    private Button button1;
    private Button button2;
    private Button button3;
    private VBox centerLayout;
    private final String defaultRightSideButton = "-fx-background-color: #DDA0DD; -fx-text-fill: white; -fx-padding: 20; -fx-cursor: hand";
    private final String selectedRightSideButton = "-fx-background-color: #9135dc; -fx-text-fill: white; -fx-padding: 20; -fx-cursor: hand";

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        String temLoginUsername = username;
        String temRegisterUsername = userTextField.getText();
        primaryStage.setTitle("欢迎来到普通客户个人页面");

        // 设置窗口图标
        primaryStage.getIcons().clear(); // 清除现有图标
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/common_customer_icon.png")));

        // 总布局
        BorderPane mainLayout = new BorderPane();

        // 顶部区域控件
        Label label = new Label("普通客户系统");
        label.setMinWidth(130);
        label.setStyle("-fx-text-fill: white");
        label.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 20));

        //图标
        Image image = new Image(getClass().getResourceAsStream("/common_customer.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);

        // 登出按钮
        Button logoutButton = new Button("退出登录", createImageView("/logout_icon_black.png"));
        logoutButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-cursor: hand;");
        logoutButton.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 14));
        logoutButton.setMinWidth(100);
        logoutButton.setOnMouseEntered(e -> logoutButton.setStyle("-fx-background-color: #FFA500; -fx-text-fill: black; -fx-cursor: hand;"));
        logoutButton.setOnMouseExited(e -> logoutButton.setStyle("-fx-background-color: #FFD700; -fx-text-fill: black; -fx-cursor: hand;"));
        logoutButton.setOnAction(e -> {
            new login().start(primaryStage);
            username = null;
        });

        //泥嚎！XXX
        Label userLabel;
        if (!loginFlag) {
            userLabel = new Label("登录  |  注册");
            userLabel.setStyle("-fx-text-fill: white;-fx-font-size: 20px; -fx-padding: 5px");
        }
        if (temLoginUsername != null){
            userLabel = new Label("您好！"+ temLoginUsername);
            temLoginUsername = null;
        } else {
            userLabel = new Label("您好！" + temRegisterUsername);
        }
        userLabel.setStyle("-fx-text-fill: white;-fx-font-size: 20px; -fx-padding: 5px");

        // 顶部区域放置
        HBox tophbox = new HBox();
        tophbox.setAlignment(Pos.CENTER_LEFT);
        tophbox.getChildren().addAll(imageView, label, logoutButton);
        tophbox.setSpacing(10);

        HBox topHBox = new HBox();
        topHBox.setSpacing(980);
        topHBox.setPadding(new Insets(10));
        topHBox.setStyle("-fx-background-color: #9135dc");
        topHBox.getChildren().addAll(tophbox, userLabel);
        mainLayout.setTop(topHBox);

        // 右侧侧边栏区域控件
        //按钮
        button1 = createRightSideButton("物品选购", "/purchase_icon.png");
        button2 = createRightSideButton("购物车", "/shopping_cart_icon.png");
        button3 = createRightSideButton("个人信息", "/person_info_icon.png");
        //按钮设置初始和鼠标点击样式
        button1.setStyle(selectedRightSideButton);
        button2.setStyle(defaultRightSideButton);
        button3.setStyle(defaultRightSideButton);
        // 右侧侧边栏按钮触发事件
        addRightSideButtonClick(button1, button1, new CommonProductSelectionView());
        addRightSideButtonClick(button2, button2, new CommonShoppingCartView());
        addRightSideButtonClick(button3, button3, new CommonPersonalInfoView());

        // 右侧侧边栏区域放置
        VBox rightVBox = new VBox();
        rightVBox.getChildren().addAll(button1, button2, button3);
        rightVBox.setSpacing(20);
        rightVBox.setPadding(new Insets(20));
        rightVBox.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #f5f5ff, #e6e6fa);");
        mainLayout.setRight(rightVBox);

        // 中间区域
        centerLayout = new VBox();
        centerLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setPadding(new Insets(20));
        centerLayout.setStyle("-fx-background-color: #f8f8ff;");
        centerLayout.getChildren().add(new CommonProductSelectionView());
        mainLayout.setCenter(centerLayout);

        // 页脚区域
        // java图标
        ImageView javaIcon = new ImageView(new Image(getClass().getResourceAsStream("/java.png")));
        javaIcon.setFitWidth(30);
        javaIcon.setFitHeight(30);
        // 文字
        Label footerLabel = new Label("© 2024 普通客户系统（不用GPT就不队） |  Based on Java");
        footerLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 16));
        footerLabel.setTextFill(Color.WHITE);
        footerLabel.setPadding(new Insets(10));
        footerLabel.setAlignment(Pos.CENTER);
        //放置
        VBox footerLayout = new VBox();
        footerLayout.setPadding(new Insets(10));
        footerLayout.setStyle("-fx-background-color: #333333;");
        footerLayout.setAlignment(Pos.CENTER);

        HBox footerBox = new HBox(5, footerLabel, javaIcon);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(5));

        footerLayout.getChildren().addAll(footerBox);
        mainLayout.setBottom(footerLayout);

        Scene scene = new Scene(mainLayout, 1442, 800);
        primaryStage.setScene(scene);
        primaryStage.show();

        // 设置UserData，以便CommonShoppingCartView可以访问CommonCustomerInfo实例
        primaryStage.setUserData(this);
    }


    //创建右侧侧边栏图标
    private Button createRightSideButton(String text, String iconPath) {
        Button button = new Button(text);
        Image iconImage = new Image(getClass().getResourceAsStream(iconPath));
        ImageView iconView = new ImageView(iconImage);
        iconView.setFitWidth(25);
        iconView.setFitHeight(25);
        button.setGraphic(iconView);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 16));
        return button;
    }

    //右侧侧边栏按钮触发事件
    private void addRightSideButtonClick(Button button, Button currentButton, Pane content) {
        button.setOnAction(e -> {
            updateButtonStyles(currentButton);
            centerLayout.getChildren().clear();
            centerLayout.getChildren().add(content);
        });

        //鼠标放置 离开按钮效果
        button.setOnMouseEntered(e -> {
            if (!button.getStyle().equals(selectedRightSideButton)) {
                button.setStyle("-fx-background-color: #BA55D3; -fx-text-fill: white; -fx-padding: 20; -fx-cursor: hand;");
            }
        });
        button.setOnMouseExited(e -> {
            if (!button.getStyle().equals(selectedRightSideButton)) {
                button.setStyle(defaultRightSideButton);
            }
        });
    }

    public void updateButtonStyles(Button selectedButton) {
        button1.setStyle(defaultRightSideButton);
        button2.setStyle(defaultRightSideButton);
        button3.setStyle(defaultRightSideButton);
        selectedButton.setStyle(selectedRightSideButton);
    }

    public void updateButtonStyles(int selectedIndex) {
        button1.setStyle(defaultRightSideButton);
        button2.setStyle(defaultRightSideButton);
        button3.setStyle(defaultRightSideButton);
        switch (selectedIndex) {
            case 1 -> button1.setStyle(selectedRightSideButton);
            case 2 -> button2.setStyle(selectedRightSideButton);
            case 3 -> button3.setStyle(selectedRightSideButton);
        }
    }

    //创建按钮图标
    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }

    public static void main(String[] args) {launch(args);}
}