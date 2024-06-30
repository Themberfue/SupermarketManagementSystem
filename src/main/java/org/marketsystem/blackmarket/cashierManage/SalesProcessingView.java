package org.marketsystem.blackmarket.cashierManage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.marketsystem.blackmarket.utils.MyAlert;

import java.util.Arrays;

/**
 * @author: 朝槿
 * @date: 2024/6/20 12:18
 * @description:
 */
public class SalesProcessingView extends StackPane {

    private VBox recordsVbox;
    private SalesRecord[] records;
    private SalesRecord[] originalRecords;

    public SalesProcessingView() {
        VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.TOP_CENTER);
        centerLayout.setSpacing(5);
        //总共3个box

        Label title = new Label("销售处理");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 30));

        //创建一个文本输入框用于搜索
        TextField searchField = new TextField();
        searchField.setPromptText("搜索销售记录...");
        searchField.setMinWidth(850);
        searchField.setStyle("-fx-font-size: 13px; -fx-padding: 8px;");

        // 创建搜索按钮
        Button searchButton = new Button("搜索", createImageView("/search_icon_white.png"));
        searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #336699; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 8px 20px; -fx-cursor: hand"));

        searchButton.setOnAction(e -> {
            filterSalesRecords(searchField.getText());
            searchField.setText("");
        });

        // 创建水平盒子用于容纳搜索框和按钮
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(15);
        searchBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-padding: 13px");

        VBox layout = new VBox(10, title, searchBox);
        layout.setAlignment(Pos.CENTER);

        // 销售记录列表
        Label salesRecordsLabel = new Label("销售记录");
        salesRecordsLabel.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 20));
        salesRecordsLabel.setTextFill(Color.web("#555"));

        //第二个
        recordsVbox = new VBox();
        recordsVbox.setSpacing(10);
        recordsVbox.setPadding(new Insets(10));
        recordsVbox.getChildren().addAll(salesRecordsLabel);
        recordsVbox.setAlignment(Pos.CENTER);

        ScrollPane scrollPane = new ScrollPane(recordsVbox);
        scrollPane.setFitToWidth(true);

        // 功能按钮区
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setSpacing(30);

        Button refreshButton = new Button("刷新", createImageView("/refresh_icon.png"));
        refreshButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;");
        refreshButton.setOnMouseEntered(e -> refreshButton.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        refreshButton.setOnMouseExited(e -> refreshButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        refreshButton.setOnAction(e -> refreshSalesRecords());

        Button resetButton = new Button("重置", createImageView("/reset_icon.png"));
        resetButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;");
        resetButton.setOnMouseEntered(e -> resetButton.setStyle("-fx-background-color: #e0a800; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        resetButton.setOnMouseExited(e -> resetButton.setStyle("-fx-background-color: #ffc107; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        resetButton.setOnAction(e -> MyAlert.alertSet("哎哟，你干嘛！","","达咩重置！"));

        Button exportButton = new Button("导出", createImageView("/export_icon.png"));
        exportButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;");
        exportButton.setOnMouseEntered(e -> exportButton.setStyle("-fx-background-color: #0056b3; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        exportButton.setOnMouseExited(e -> exportButton.setStyle("-fx-background-color: #007bff; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 8px 15px;"));
        exportButton.setOnAction(e -> MyAlert.alertSet("哎哟，你干嘛！", "", "达咩导"));

        //第三个
        buttonBox.getChildren().addAll(refreshButton, resetButton, exportButton);

        // 添加到布局中ming
        centerLayout.getChildren().addAll(layout, scrollPane, buttonBox);
        this.getChildren().add(centerLayout);

        // 生成示例销售记录
        generateSampleSalesRecords();
    }

    private void generateSampleSalesRecords() {
        // 示例销售记录数据
        originalRecords = new SalesRecord[]{
                new SalesRecord("2024-06-20", "订单21331435345", "刘坤中", "已出库"),
                new SalesRecord("2024-06-21", "订单12332577234", "呈青玄", "未出库"),
                new SalesRecord("2024-06-22", "订单32554352342", "郭哈哈", "未出库"),
                new SalesRecord("2024-06-23", "订单34534234324", "学委", "未出库"),
        };
        records = originalRecords.clone();
        displayRecords(records);
    }

    private void displayRecords(SalesRecord[] recordsToDisplay) {
        recordsVbox.getChildren().clear();
        for (SalesRecord record : recordsToDisplay) {
            VBox recordBox = createRecordBox(record);
            recordsVbox.getChildren().add(recordBox);
        }
    }

    private VBox createRecordBox(SalesRecord record) {
        VBox recordBox = new VBox();
        recordBox.setSpacing(5);
        recordBox.setPadding(new Insets(10));
        recordBox.setStyle("-fx-background-color: #ffffff; -fx-border-radius: 10px; -fx-padding: 10px;");

        // 根据出库状态设置边框颜色和内部颜色
        String borderColor = record.getStatus().equals("已出库") ? "#28a745" : "#dc3545";
        String backgroundColor = record.getStatus().equals("已出库") ? "#e6ffe6" : "#ffe6e6";
        recordBox.setStyle("-fx-border-color: " + borderColor + "; -fx-border-width: 2px; -fx-background-color: " + backgroundColor + "; -fx-border-radius: 10px;");

        // 添加阴影效果
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.4, 0.5, 0.5));
        recordBox.setEffect(shadow);

        Label dateLabel = new Label("日期: " + record.getDate());
        dateLabel.setFont(Font.font("Microsoft YaHei", 14));
        Label orderLabel = new Label("订单号: " + record.getOrderNumber());
        orderLabel.setFont(Font.font("Microsoft YaHei", 14));
        Label customerLabel = new Label("客户: " + record.getCustomerName() + "（Plus客户）");
        customerLabel.setTextFill(Color.web("#FF1493"));
        customerLabel.setFont(Font.font("Microsoft YaHei", 14));
        Label statusLabel = new Label("状态: " + record.getStatus());
        statusLabel.setFont(Font.font("Microsoft YaHei", 14));
        statusLabel.setTextFill(record.getStatus().equals("已出库") ? Color.GREEN : Color.RED);

        recordBox.getChildren().addAll(dateLabel, orderLabel, customerLabel, statusLabel);
        return recordBox;
    }
    // 创建图像
    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
        return imageView;
    }
    private void filterSalesRecords(String searchText) {
        SalesRecord[] filteredRecords = Arrays.stream(records)
                .filter(record -> record.getOrderNumber().contains(searchText) || record.getCustomerName().contains(searchText))
                .toArray(SalesRecord[]::new);
        displayRecords(filteredRecords);
    }

    private void refreshSalesRecords() {
        records = originalRecords.clone();
        displayRecords(records);
    }

    private class SalesRecord {
        private String date;
        private String orderNumber;
        private String customerName;
        private String status;

        public SalesRecord(String date, String orderNumber, String customerName, String status) {
            this.date = date;
            this.orderNumber = orderNumber;
            this.customerName = customerName;
            this.status = status;
        }

        public String getDate() {
            return date;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public String getCustomerName() {
            return customerName;
        }

        public String getStatus() {
            return status;
        }
    }
}
