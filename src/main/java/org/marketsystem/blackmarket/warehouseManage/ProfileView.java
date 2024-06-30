package org.marketsystem.blackmarket.warehouseManage;

import javafx.application.HostServices;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ProfileView extends StackPane {
    private HostServices hostServices;

    public ProfileView(HostServices hostServices) {
        this.hostServices = hostServices;

        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 20, 20, 20));
        root.setStyle("-fx-background-color: #f4f4f4;");

        String[][][] peopleInfo = {
                {
                        {"姓名：", "Themberfue", "/name_icon.png"},
                        {"电话：", "12377                  ", "/phone_icon.png"},
                        {"性别：", "男                     ", "/gender_icon.png"},
                        {"地址：", "圣丹尼斯", "/address_icon.png"},
                        {"邮箱：", "L2569075708h@outlook.com", "/email_icon.png"},
                        {"博客：", "CSDN个人主页", "https://blog.csdn.net/Themberfue?spm=1010.2135.3001.5343", "/csdn_icon.png"},
                        {"个人网站：", "个人网站", "https://www.themberfue.cn", "/website_icon.png"},
                        {"Gitee：", "Gitee个人主页", "https://gitee.com/themberfue_0", "/Gitee_icon.png"},
                        {"GitHub：", "Github个人主页", "https://github.com/Themberfue", "/Github_icon.png"}
                },
                {
                        {"姓名：", "Pxoolcm", "/name_icon.png"},
                        {"电话：", "+86 18888888888", "/phone_icon.png"},
                        {"性别：", "男", "/gender_icon.png"},
                        {"地址：", "LoSantos", "/address_icon.png"},
                        {"邮箱：", "goundprice@gmail.com", "/email_icon.png"},
                        {"博客：", "CSDN个人主页", "https://blog.csdn.net/2303_79441076?spm=1000.2115.3001.5343", "/csdn_icon.png"},
                        {"个人网站：", "个人网站", "https://www.example.com", "/website_icon.png"},
                        {"Gitee：", "Gitee个人主页", "https://gitee.com/liao-lingchun", "/Gitee_icon.png"},
                        {"GitHub：", "Github个人主页", "https://github.com/Pxoolcm30", "/Github_icon.png"}
                },
                {
                        {"姓名：", "Zhaojin", "/name_icon.png"},
                        {"电话：", "0795-32019267  ", "/phone_icon.png"},
                        {"性别：", "男", "/gender_icon.png"},
                        {"地址：", "28°03′17″N 115°32′46″E", "/address_icon.png"},
                        {"邮箱：", "lukuizhaojin@gmail", "/email_icon.png"},
                        {"博客：", "CSDN个人主页", "https://blog.csdn.net/ZYM2300837217?spm=1000.2115.3001.5343", "/csdn_icon.png"},
                        {"个人网站：", "个人网站", "https://www.example.com", "/website_icon.png"},
                        {"Gitee：", "Gitee个人主页", "https://gitee.com/wangwu", "/Gitee_icon.png"},
                        {"GitHub：", "Github个人主页", "https://github.com/wangwu", "/Github_icon.png"}
                }
        };

        String[] imagePaths = {
                "/HeadImageLJH.png",
                "/HeadImageLLC.png",
                "/HeadImageZYM.png",
        };

        for (int i = 0; i < peopleInfo.length; i++) {
            VBox personBox = createPersonBox(peopleInfo[i], imagePaths[i]);
            root.getChildren().add(personBox);
        }

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(20));

        this.getChildren().add(scrollPane);
    }

    private VBox createPersonBox(String[][] info, String imagePath) {
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #007bff; -fx-background-radius: 10 10 0 0; -fx-text-fill: white;");

        ImageView profileImage = new ImageView(new Image(imagePath));
        profileImage.setFitWidth(150);
        profileImage.setFitHeight(150);
        profileImage.setPreserveRatio(true);
        profileImage.setStyle("-fx-border-color: white; -fx-border-width: 5px; -fx-border-radius: 50%;");

        Text name = new Text(info[0][1]);
        name.setFont(Font.font(30));
        name.setFill(Color.WHITE);

        Text title = new Text("软件硬化工程 | 爱好摄金");
        title.setFont(Font.font(16));
        title.setFill(Color.WHITE);

        header.getChildren().addAll(profileImage, name, title);

        GridPane infoGrid = new GridPane();
        infoGrid.setPadding(new Insets(20));
        infoGrid.setHgap(20);
        infoGrid.setVgap(20);

        for (int i = 0; i < info.length; i++) {
            HBox infoBox = new HBox(10);
            infoBox.setPadding(new Insets(10));
            infoBox.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(10), Insets.EMPTY)));
            infoBox.setStyle("-fx-border-radius: 10; -fx-background-radius: 10; -fx-border-width: 1; -fx-border-color: #ddd; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");

            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(info[i][info[i].length - 1])));
            icon.setFitWidth(20);
            icon.setFitHeight(20);
            icon.setPreserveRatio(true);

            Text label = new Text(info[i][0]);
            label.setFont(Font.font(14));
            label.setFill(Color.web("#007bff"));

            if (info[i].length == 4 && info[i][2].startsWith("http")) { // 包含链接和显示文本
                Hyperlink link = new Hyperlink(info[i][1]);
                link.setFont(Font.font(14));
                String url = info[i][2];
                link.setOnAction(e -> hostServices.showDocument(url));
                infoBox.getChildren().addAll(icon, label, link);
            } else {
                Text value = new Text(info[i][1]);
                value.setFont(Font.font(14));
                infoBox.getChildren().addAll(icon, label, value);
            }

            infoGrid.add(infoBox, i % 5, i / 5);
        }

        VBox infoContainer = new VBox();
        infoContainer.setPadding(new Insets(20));
        infoContainer.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0, 0, 10, 10, false), Insets.EMPTY)));
        infoContainer.setAlignment(Pos.CENTER);
        infoContainer.getChildren().add(infoGrid);

        VBox personBox = new VBox(0);
        personBox.setAlignment(Pos.TOP_CENTER);
        personBox.getChildren().addAll(header, infoContainer);
        personBox.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-width: 1; -fx-border-color: #ddd; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 0);");
        return personBox;
    }
}