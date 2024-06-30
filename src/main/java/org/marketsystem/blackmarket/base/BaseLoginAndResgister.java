package org.marketsystem.blackmarket.base;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import static org.marketsystem.blackmarket.loginAndRegister.login.HEIGHT;
import static org.marketsystem.blackmarket.loginAndRegister.login.WIDTH;

/**
 * @author: Themberfue
 * @date: 2024/6/24 22:34
 * @description:
 */
abstract public class BaseLoginAndResgister extends Application {
    protected GridPane grid;
    protected Text sceneTitle;
    protected HBox titleBox;
    protected Label userNameLabel;
    protected Label passwordLabel;
    protected HBox hBox;
    protected VBox vbox;
    protected HBox hBox1;
    protected Rectangle clip;
    protected StackPane root;
    protected Scene scene;

    /**
     * 构建初始 UI界面
     * @param primaryStage
     */
    abstract protected void createUI(Stage primaryStage);

    /**
     * 创建网格布局
     * @param primaryStage
     */
    abstract protected void createGridComponent(Stage primaryStage);

    /**
     * 创建头部区域
     */
    abstract protected void createHeaderComponent();

    /**
     * 创建中部上半区域
     */
    abstract protected void createMiddleTopComponent();
    
    /**
     * 创建中部下半区域
     */
    abstract protected void createMiddleBottomComponent();
    
    /**
     * 创建文本框
     */
    abstract protected void createFieldComponent();
    
    /**
     * 创建按钮
     */
    abstract protected void createButtonComponent();

    /**
     * 超链接点击事件方法
     * @param primaryStage
     */
    abstract protected void hyperLinkClickAction(Stage primaryStage);

    /**
     * 按钮点击事件方法
     * @param primaryStage
     */
    abstract protected void buttonClickAction(Stage primaryStage);

    /**
     * 创建左侧边图片
     */
    protected void createLeftSideImageComponent() {
        // 加载图像
        ImageView loginImageView = createImageComponent("/loginbackg.png", 299, 400);

        // 创建一个圆角矩形作为剪辑
        clip = new Rectangle(loginImageView.getFitWidth(), loginImageView.getFitHeight());
        clip.setArcWidth(20);  // 设置圆角的宽度
        clip.setArcHeight(20); // 设置圆角的高度

        // 应用剪辑到 ImageView
        loginImageView.setClip(clip);

        hBox1 = new HBox(loginImageView, vbox);
        hBox1.setStyle("-fx-background-color: white; -fx-border-width: 1px; -fx-border-color: white; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        hBox1.setMaxWidth(799);
        hBox1.setMaxHeight(400);
    }

    /**
     * 创建布局背景
     * @param primaryStage
     */
    protected void createBackGroundComponent(Stage primaryStage) {
        // 创建 StackPane 作为根布局，并添加背景图
        root = new StackPane();

        ImageView backgroundImageView = createImageComponent("/backgr.png", WIDTH, HEIGHT);

        // 添加背景图像和 VBox 到 StackPane
        root.getChildren().addAll(backgroundImageView, hBox1);

        // 设置场景
        scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
    }

    /**
     * 创建图片
     * @param path
     * @param width
     * @param height
     * @return ImageView
     */
    protected ImageView createImageComponent(String path, int width, int height) {
        Image image = new Image(getClass().getResourceAsStream(path));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    /**
     * Application -> start方法
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        createUI(primaryStage);
    }
}
