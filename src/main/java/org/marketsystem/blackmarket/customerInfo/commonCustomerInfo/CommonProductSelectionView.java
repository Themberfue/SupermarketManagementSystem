package org.marketsystem.blackmarket.customerInfo.commonCustomerInfo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.marketsystem.blackmarket.dataSheet.Product;
import org.marketsystem.blackmarket.utils.MyAlert;
import org.marketsystem.blackmarket.utils.MysqlCon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static javafx.application.Application.launch;

/**
 * @className: ProductSelectionView
 * @author: 朝槿
 * @date: 2024/6/23 13:37
 */
public class CommonProductSelectionView extends Pane {
    // 商品表格
    TableView<Product> tableView;
    public CommonProductSelectionView() {
        // 标题
        Label label = new Label("商品选购");
        label.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 30));
        label.setTextFill(Color.DARKBLUE);

        // 搜索框
        TextField searchField = new TextField();
        searchField.setPromptText("搜索物品名称...");
        searchField.setMinWidth(800);
        searchField.setStyle("-fx-font-size: 15px; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        // 搜索按钮
        Button searchButton = new Button("搜索");
        searchButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #975ed5; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));
        searchButton.setOnAction(e -> MyAlert.alertSet("功能暂未开发，","敬请期待", "...."));

        // 创建水平盒子用于容纳搜索框和按钮
        HBox searchBox = new HBox(searchField, searchButton);
        searchBox.setPadding(new Insets(10));
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setSpacing(10);
        searchBox.setPrefWidth(1200);
        searchBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #dcdcdc; -fx-border-radius: 5px; -fx-padding: 10px;");

        tableView = new TableView<>();

        TableColumn<Product, String> productIdColumn = new TableColumn<>("商品ID");
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

        TableColumn<Product, String> productNameColumn = new TableColumn<>("商品名称");
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("商品描述");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("product_description"));
        descriptionColumn.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
            @Override
            public TableCell<Product, String> call(TableColumn<Product, String> productStringTableColumn) {
                return new TableCell<>() {
                    private Text text;
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setGraphic(null);
                        } else {
                            if (text == null) {
                                text = new Text();
                                text.setWrappingWidth(735);
                            }
                            text.setText(item);
                            setGraphic(text);
                        }
                    }
                };
            }
        });

        TableColumn<Product, Double> priceColumn = new TableColumn<>("商品价格");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stockColumn = new TableColumn<>("商品库存");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stocks"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("数量");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Void> actionColumn = new TableColumn<>("操作");
        actionColumn.setCellFactory(param -> new TableCell<Product, Void>() {
            private final Button addButton = new Button("", createImageView("/add_icon.png"));
            private final Button subtractButton = new Button("", createImageView("/dele_icon.png"));

            {
                addButton.setShape(new Circle(5));
                addButton.setStyle("-fx-background-color: white; -fx-cursor: hand");
                subtractButton.setShape(new Circle(5));
                subtractButton.setStyle("-fx-background-color: white; -fx-cursor: hand");

                addButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    product.setQuantity(product.getQuantity() + 1);
                    getTableView().refresh();
                });
                subtractButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    if (product.getQuantity() > 0) {
                        product.setQuantity(product.getQuantity() - 1);
                        getTableView().refresh();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox box = new HBox(subtractButton, addButton);
                    box.setSpacing(10);
                    setGraphic(box);
                }
            }
        });

        tableView.getColumns().addAll(productIdColumn, productNameColumn, descriptionColumn, priceColumn, stockColumn, quantityColumn, actionColumn);

        refreshTableProductData();

        // 查看商品按钮
        Button viewButton = new Button("查看商品");
        viewButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;");
        viewButton.setOnMouseEntered(e -> viewButton.setStyle("-fx-background-color: #975ed5; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));
        viewButton.setOnMouseExited(e -> viewButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));

        viewButton.setOnAction(e -> viewProduct());

        // 结账按钮
        Button settleButton = new Button("结账");
        settleButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;");
        settleButton.setOnMouseEntered(e -> settleButton.setStyle("-fx-background-color: #975ed5; -fx-text-fill: white; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));
        settleButton.setOnMouseExited(e -> settleButton.setStyle("-fx-background-color: #9135dc; -fx-text-fill: #ffffff; -fx-font-size: 15px; -fx-padding: 10px 20px; -fx-background-radius: 5px; -fx-cursor: hand;"));
        settleButton.setOnAction(e -> MyAlert.alertSet("功能暂未开发，","敬请期待", "...."));

        // 底部功能按钮
        HBox bottomButtonHBox = new HBox(50, viewButton, settleButton);
        bottomButtonHBox.setAlignment(Pos.CENTER);
        bottomButtonHBox.setPadding(new Insets(10));

        // 放置
        VBox vbox = new VBox(label, searchBox, tableView, bottomButtonHBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f8f8ff;");

        this.getChildren().add(vbox);
    }

    private ImageView createImageView(String path) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
        imageView.setFitWidth(23);
        imageView.setFitHeight(23);
        return imageView;
    }

    // 刷新数据
    private void refreshTableProductData() {
        try {
            String sql = "SELECT * FROM product";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ResultSet resultSet = ppst.executeQuery();
            ObservableList<Product> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Product product = new Product(
                        resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stocks"));
                list.add(product);
            }
            MysqlCon.closeCon(conn,ppst,resultSet);
            tableView.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 查看商品
    private void viewProduct() {
        Product selectedProduct = tableView.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("警告");
            alert.setHeaderText(null);
            alert.setContentText("请选择要查看的订单！");
            alert.showAndWait();
            return;
        }
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("查看订单");

// 商品描述使用 TextArea 显示长文本
        TextArea productDescriptionArea = new TextArea(selectedProduct.getProduct_description());
        productDescriptionArea.setWrapText(true);
        productDescriptionArea.setEditable(false);
        productDescriptionArea.setPrefRowCount(3);
        productDescriptionArea.setPrefWidth(400);
        productDescriptionArea.setStyle("-fx-font-size: 16px; -fx-background-color: #EDE7F6; -fx-border-color: #D1C4E9; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); -fx-focus-color: transparent;");

        Label descriptionLabel = new Label("商品描述:");
        descriptionLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #673AB7;");
        VBox descriptionBox = new VBox(descriptionLabel, productDescriptionArea);
        descriptionBox.setPadding(new Insets(10));
        descriptionBox.setStyle("-fx-background-color: #EDE7F6; -fx-border-color: #D1C4E9; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox vbox = new VBox(15,
                createInfoBox("商品ID:", selectedProduct.getProduct_id(), "#F3E5F5", "#E1BEE7", "#8E24AA"),
                createInfoBox("商品名称:", selectedProduct.getProduct_name(), "#F3E5F5", "#E1BEE7", "#8E24AA"),
                descriptionBox,
                createInfoBox("商品位置:", selectedProduct.getProduct_location(), "#EDE7F6", "#D1C4E9", "#5E35B1"),
                createInfoBox("商品价格:", String.valueOf(selectedProduct.getPrice()), "#EDE7F6", "#D1C4E9", "#5E35B1"),
                createInfoBox("库存数量:", String.valueOf(selectedProduct.getStocks()), "#EDE7F6", "#D1C4E9", "#5E35B1")
        );

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #EDE7F6; -fx-border-color: #D1C4E9; -fx-border-radius: 10px; -fx-border-width: 2px;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        Scene scene = new Scene(scrollPane, 550, 475);
        dialog.setScene(scene);
        dialog.showAndWait();

    }

    private HBox createInfoBox(String labelText, String contentText, String bgColor, String borderColor, String textColor) {
        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + textColor + ";");

        Label content = new Label(contentText);
        content.setWrapText(true);
        content.setStyle("-fx-font-size: 14px; -fx-text-fill: " + textColor + ";");

        HBox infoBox = new HBox(5, label, content);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-background-color: " + bgColor + "; -fx-border-color: " + borderColor + "; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        return infoBox;
    }
}
