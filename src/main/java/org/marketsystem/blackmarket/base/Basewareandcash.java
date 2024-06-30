package org.marketsystem.blackmarket.base;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * @className: WhandCsandCtm
 * @author: 朝槿
 * @date: 2024/6/26 10:16
 */
abstract public class Basewareandcash extends Application {
    public void createFooterComponent(){
        // 创建Java图标
        ImageView javaIcon = new ImageView(new Image(getClass().getResourceAsStream("/java.png")));
        javaIcon.setFitWidth(30);
        javaIcon.setFitHeight(30);

        // 创建页脚
        Label footerLabel = new Label("© 2024 仓库管理系统（不用GPT就不队） |  Based on Java");
        footerLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 16));
        footerLabel.setTextFill(Color.WHITE);
        footerLabel.setPadding(new Insets(10));
        footerLabel.setAlignment(Pos.CENTER);

        HBox footerBox = new HBox(5, footerLabel, javaIcon);
        footerBox.setAlignment(Pos.CENTER);
        footerBox.setPadding(new Insets(10));

        StackPane footerPane = new StackPane(footerBox);
        footerPane.setPrefHeight(40);
        footerPane.setStyle("-fx-background-color: #333333;");
    }

    @Override
    public void start(Stage stage) throws Exception {
    }
}
