package org.marketsystem.blackmarket.warehouseManage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * @author: Themberfue
 * @date: 2024/6/20 13:00
 * @description:
 */
public class NotificationsView extends StackPane {
    public NotificationsView() {
        // 创建顶部标题
        Label titleLabel = new Label("通知和警报");
        titleLabel.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));
        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(15));

        // 创建通知列表
        VBox notificationList = new VBox(10);
        notificationList.setPadding(new Insets(20));
        notificationList.setStyle("-fx-background-color: #F5F5F5;");
        notificationList.getChildren().addAll(
                createNotification("中优先级", "订单12355还未发货，请尽快发货。", "订单状态更新", "#FFF9C4", "#FFA000"),
                createNotification("高优先级", "强效蛇油的库存已低于设定的下限，请尽快补货。", "库存不足警报", "#FFCDD2", "#d32f2f"),
                createNotification("中优先级", "传送带C12306出现故障，请立即检修。", "设备故障警报", "#FFF9C4", "#FFA000"),
                createNotification("低优先级", "订单12377已发货。", "订单状态更新", "#C8E6C9", "#388E3C"),
                createNotification("中优先级", "仓库温度超过设定值，请检查空调系统。", "环境监控警报", "#FFF9C4", "#FFA000"),
                createNotification("高优先级", "烤豆子罐头的库存已高于设定的下限，请尽快清理库存", "库存过多警报", "#FFCDD2", "#d32f2f")
        );

        // 使用ScrollPane包含通知列表
        ScrollPane scrollPane = new ScrollPane(notificationList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F5F5F5; -fx-border-width: 0;");

        // 创建主布局
        VBox mainLayout = new VBox(10, titleBox, scrollPane);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainLayout.setStyle("-fx-background-color: #ffffff;");

        this.getChildren().add(mainLayout);
    }

    private VBox createNotification(String priority, String message, String type, String color, String priorityColor) {
        Label priorityLabel = new Label(priority);
        priorityLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: " + priorityColor + ";");

        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000000;");

        Label typeLabel = new Label(type);
        typeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #757575; -fx-font-style: italic;");
        typeLabel.setAlignment(Pos.CENTER_RIGHT);

        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + priorityColor + "; -fx-font-size: 14px; -fx-border-color: " + priorityColor + "; -fx-border-width: 1px; -fx-border-radius: 15px; -fx-background-radius: 15px; -fx-cursor: hand");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle("-fx-background-color: " + priorityColor + "; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-color: " + priorityColor + "; -fx-border-width: 1px; -fx-border-radius: 15px; -fx-background-radius: 15px;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: " + priorityColor + "; -fx-font-size: 14px; -fx-border-color: " + priorityColor + "; -fx-border-width: 1px; -fx-border-radius: 15px; -fx-background-radius: 15px;"));
        closeButton.setOnAction(event -> {
            VBox notificationBox = (VBox) closeButton.getParent().getParent();
            VBox notificationList = (VBox) notificationBox.getParent();
            notificationList.getChildren().remove(notificationBox);
            if (notificationList.getChildren().isEmpty()) {
                Label noNotificationLabel = new Label("当前没有通知或警报");
                noNotificationLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #757575;");
                VBox noNotificationBox = new VBox(noNotificationLabel);
                noNotificationBox.setAlignment(Pos.CENTER);
                noNotificationBox.setPadding(new Insets(20));
                noNotificationBox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");
                notificationList.getChildren().add(noNotificationBox);
            }
        });

        HBox headerBox = new HBox(priorityLabel, closeButton);
        HBox.setHgrow(priorityLabel, Priority.ALWAYS);
        headerBox.setAlignment(Pos.TOP_RIGHT);
        headerBox.setSpacing(10);

        HBox typeBox = new HBox(typeLabel);
        typeBox.setAlignment(Pos.CENTER_RIGHT);

        VBox notificationBox = new VBox(5, headerBox, messageLabel, typeBox);
        notificationBox.setPadding(new Insets(10));
        notificationBox.setStyle("-fx-background-color: " + color + "; -fx-border-color: #e0e0e0; -fx-border-width: 1px; -fx-background-radius: 10px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        return notificationBox;
    }
}