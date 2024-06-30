package org.marketsystem.blackmarket.customerInfo.commonCustomerInfo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CommonShoppingCartView extends Pane {

    public CommonShoppingCartView() {
        // 图像
        Image image = new Image("/shoppingcar.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);

        // 标签
        Label label2 = new Label("您的购物车空空如也");
        label2.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 15));

        Label label3 = new Label("再忙，也要记得买点什么犒劳自己~");
        label3.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 13));

        // 按钮
        Button buyButton = new Button("去逛逛");
        buyButton.setMinWidth(220);
        buyButton.setStyle("-fx-background-color: #ff5000; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-weight: BOLD;");
        buyButton.setOnMouseEntered(e -> buyButton.setStyle("-fx-background-color: #ca4407; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-weight: BOLD;"));
        buyButton.setOnMouseExited(e -> buyButton.setStyle("-fx-background-color: #ff5000; -fx-text-fill: #ffffff; -fx-font-size: 20px; -fx-padding: 10px 20px; -fx-background-radius: 8px; -fx-cursor: hand; -fx-font-weight: BOLD;"));

        // 布局
        VBox vbox = new VBox(imageView, label2, label3, buyButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));

        // 使用AnchorPane确保VBox居中对齐
        AnchorPane anchorPane = new AnchorPane(vbox);
        AnchorPane.setTopAnchor(vbox, 100.0);
        AnchorPane.setBottomAnchor(vbox, 0.0);
        AnchorPane.setLeftAnchor(vbox, 330.0);
        AnchorPane.setRightAnchor(vbox, 0.0);
        anchorPane.setPrefSize(1000, 0);

        this.getChildren().add(anchorPane);

        buyButton.setOnAction(e -> {
            // 清除所有内容
            Pane parent = (Pane) this.getParent();
            parent.getChildren().clear();
            parent.getChildren().add(new CommonProductSelectionView());

            // 更新按钮样式
            CommonCustomerInfo commonCustomerInfo = (CommonCustomerInfo) parent.getScene().getWindow().getUserData();
            commonCustomerInfo.updateButtonStyles(1);
        });
    }
}