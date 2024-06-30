package org.marketsystem.blackmarket.cashierManage;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.KeyValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author: 朝槿
 * @date: 2024/6/20 12:19
 * @description:
 */
public class AfterSalesServiceView extends StackPane {
    private VBox aftersalesRecords;
    private List<HBox> recordBoxes;
    private Random random = new Random();

    private static final String[] DESCRIPTIONS = {
            "产品出现问题，客户要求退换货。",
            "产品质量不佳，客户申请退款。",
            "客户收到的产品与描述不符，要求更换。",
            "产品运输过程中损坏，客户要求补发。",
            "客户反馈产品使用过程中出现故障，申请售后服务。",
            "产品缺少配件，客户要求补发。",
            "客户对产品不满意，要求退货。",
            "产品包装破损，客户要求更换。",
            "客户收到的产品有瑕疵，申请售后处理。",
            "产品无法正常使用，客户要求技术支持。"
    };

    public AfterSalesServiceView(Stage primaryStage) {
        VBox centerLayout = new VBox();
        centerLayout.setAlignment(Pos.CENTER);
        centerLayout.setSpacing(10);

        // 创建标题
        Label title = new Label("售后服务");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 30));

        // 售后记录列表
        Label aftersalesRecordsLabel = new Label("售后列表清单");
        aftersalesRecordsLabel.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 20));
        aftersalesRecordsLabel.setTextFill(Color.web("#555"));
        aftersalesRecordsLabel.setAlignment(Pos.CENTER);

        // 初始化售后记录容器
        aftersalesRecords = new VBox();
        aftersalesRecords.setSpacing(10);
        aftersalesRecords.setPadding(new Insets(10));
        aftersalesRecords.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1px; -fx-border-radius: 10px;");

        // 初始化记录列表
        recordBoxes = new ArrayList<>();

        // 示例售后记录（可以用实际数据替换）
        for (int i = 1; i <= 50; i++) {
            addRecord("售后记录 " + i + ": " + getRandomDescription(), primaryStage);
        }

        // 将售后记录添加到滚动面板中
        ScrollPane scrollPane = new ScrollPane(aftersalesRecords);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        VBox recordBox = new VBox(10, aftersalesRecordsLabel, scrollPane);
        recordBox.setPadding(new Insets(7));
        recordBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-width: 1px;");

        centerLayout.getChildren().addAll(title, recordBox);
        this.getChildren().add(centerLayout);
    }

    private String getRandomDescription() {
        int index = random.nextInt(DESCRIPTIONS.length);
        return DESCRIPTIONS[index];
    }

    private void addRecord(String description, Stage primaryStage) {
        Label record = new Label(description);
        record.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 16));
        record.setTextFill(Color.web("#333"));

        Button acceptButton = new Button("接受", createImageView("/accept_icon.png"));
        Button refuseButton = new Button("拒绝", createImageView("/refuse_icon.png"));
        acceptButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand");
        acceptButton.setOnMouseEntered(e -> acceptButton.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand"));
        acceptButton.setOnMouseExited(e -> acceptButton.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand"));

        refuseButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand");
        refuseButton.setOnMouseEntered(e -> refuseButton.setStyle("-fx-background-color: #c82333; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand"));
        refuseButton.setOnMouseExited(e -> refuseButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-size: 12px; -fx-padding: 5px 15px; -fx-cursor: hand"));

        HBox buttonsBox = new HBox(10, acceptButton, refuseButton);
        buttonsBox.setAlignment(Pos.CENTER_RIGHT);

        HBox recordBox = new HBox();
        recordBox.setPadding(new Insets(10));
        recordBox.setStyle("-fx-background-color: #e6f7ff; -fx-border-color: #1890ff; -fx-border-width: 2px; -fx-border-radius: 10px;");

        // 添加阴影效果
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.4, 0.5, 0.5));
        recordBox.setEffect(shadow);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        recordBox.getChildren().addAll(record, spacer, buttonsBox);

        acceptButton.setOnAction(e -> {
            aftersalesRecords.getChildren().remove(recordBox);
            showNotification("接受成功", primaryStage, Color.GREEN);
        });

        refuseButton.setOnAction(e -> {
            aftersalesRecords.getChildren().remove(recordBox);
            showNotification("拒绝成功", primaryStage, Color.RED);
        });

        recordBoxes.add(recordBox);
        aftersalesRecords.getChildren().add(recordBox);
    }

    private void showNotification(String message, Stage primaryStage, Color color) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);

        // 添加阴影效果
        DropShadow shadow = new DropShadow();
        shadow.setRadius(5.0);
        shadow.setOffsetX(3.0);
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.color(0.4, 0.5, 0.5));

        Label notificationLabel = new Label(message);
        notificationLabel.setFont(Font.font("Microsoft YaHei", FontWeight.NORMAL, 15));
        notificationLabel.setStyle("-fx-background-color: " + toHexString(color) + "; -fx-text-fill: white; -fx-padding: 10px; -fx-border-radius: 5px; -fx-border-width: 1px; -fx-border-color: white");
        notificationLabel.setOpacity(0);
        notificationLabel.setEffect(shadow);
        

        StackPane popupContent = new StackPane(notificationLabel);
        popupContent.setStyle("-fx-background-color: transparent;");
        popup.getContent().add(popupContent);

        popup.show(primaryStage);

        // 设置位置为右上角
        popup.setX(primaryStage.getX() + primaryStage.getWidth() - 180);
        popup.setY(primaryStage.getY() + 155);

        // 渐变出现
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), notificationLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // 渐变消失
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), notificationLabel);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDelay(Duration.seconds(2));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(notificationLabel.opacityProperty(), 0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(notificationLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(3), new KeyValue(notificationLabel.opacityProperty(), 1)),
                new KeyFrame(Duration.seconds(4), new KeyValue(notificationLabel.opacityProperty(), 0))
        );
        timeline.setOnFinished(event -> popup.hide());

        timeline.play();
    }

    private String toHexString(Color color) {
        int r = (int) (color.getRed() * 255);
        int g = (int) (color.getGreen() * 255);
        int b = (int) (color.getBlue() * 255);
        return String.format("#%02X%02X%02X", r, g, b);
    }
    // 创建图像
    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(18);
        imageView.setFitHeight(18);
        return imageView;
    }
}
