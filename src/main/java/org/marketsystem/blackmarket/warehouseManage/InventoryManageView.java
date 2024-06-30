package org.marketsystem.blackmarket.warehouseManage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.marketsystem.blackmarket.base.View;
import org.marketsystem.blackmarket.dataSheet.Product;
import org.marketsystem.blackmarket.utils.*;
import java.sql.*;

public class InventoryManageView extends StackPane implements View {
    // 声明table为类成员变量
    private TableView<Product> table;

    // 控制整体菜单方法
    private void addMenuItemClickHandler(Menu menu) {
        for (MenuItem menuItem : menu.getItems()) {
            menuItem.setOnAction(event -> {
                String itemName = menuItem.getText();
                try {
                    filterData(itemName);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    // 构建整体页面
    public InventoryManageView() {
        // 创建标题
        Label title = new Label("库存管理");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));

        // 创建菜单栏
        MenuBar menuBar = new MenuBar();

        // 设置菜单栏样式
        menuBar.setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-text-fill: white; -fx-background-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.5), 10, 0, 0, 2);");

        // 创建第一个菜单 "按供应商筛选"
        Menu supplierMenu = new Menu("按供应商筛选");
        supplierMenu.setStyle("-fx-text-fill: white;");
        ImageView supplierMenuIcon = new ImageView(new Image(getClass().getResourceAsStream("/screen_icon.png")));
        supplierMenuIcon.setFitWidth(16);
        supplierMenuIcon.setFitHeight(16);
        supplierMenu.setGraphic(supplierMenuIcon);

        MenuItem supplier1 = new MenuItem("瓦伦丁", createImageView("/valentine_icon.png", 16, 16));
        MenuItem supplier2 = new MenuItem("草莓镇", createImageView("/strawberry_icon.png", 16, 16));
        MenuItem supplier3 = new MenuItem("圣丹尼斯", createImageView("/saintDenis_icon.png", 16, 16));
        MenuItem supplier4 = new MenuItem("罗兹镇", createImageView("/rhodes_icon.png", 16, 16));
        supplierMenu.getItems().addAll(supplier1, supplier2, supplier3, supplier4);

        // 创建第二个菜单 "按商品类别筛选"
        Menu categoryMenu = new Menu("按商品类别筛选");
        categoryMenu.setStyle("-fx-text-fill: white;");
        ImageView categoryMenuIcon = new ImageView(new Image(getClass().getResourceAsStream("/screen_icon.png")));
        categoryMenuIcon.setFitWidth(16);
        categoryMenuIcon.setFitHeight(16);
        categoryMenu.setGraphic(categoryMenuIcon);

        MenuItem category1 = new MenuItem("服装", createImageView("/clothes_icon.png", 16, 16));
        MenuItem category2 = new MenuItem("药品及药剂", createImageView("/drug_icon.png", 16, 16));
        MenuItem category3 = new MenuItem("杂货及干货", createImageView("/cargo_icon.png", 16, 16));
        categoryMenu.getItems().addAll(category1, category2, category3);

        // 创建第三个菜单 "商品操作"
        Menu operationMenu = new Menu("商品操作");
        operationMenu.setStyle("-fx-text-fill: white;");
        ImageView operationMenuIcon = new ImageView(new Image(getClass().getResourceAsStream("/operation_icon.png")));
        operationMenuIcon.setFitWidth(16);
        operationMenuIcon.setFitHeight(16);
        operationMenu.setGraphic(operationMenuIcon);

        MenuItem operation1 = new MenuItem("增加商品", createImageView("/add_icon_black.png", 16, 16));
        MenuItem operation2 = new MenuItem("修改商品信息", createImageView("/update_icon.png", 16, 16));
        MenuItem operation3 = new MenuItem("删除商品", createImageView("/del_icon.png", 16, 16));
        MenuItem operation4 = new MenuItem("查看商品", createImageView("/view_icon.png", 16, 16));
        MenuItem operation5 = new MenuItem("搜索商品", createImageView("/search_icon.png", 16, 16));
        operationMenu.getItems().addAll(operation1, operation2, operation3, operation4, operation5);

        // 将菜单添加到菜单栏
        menuBar.getMenus().addAll(supplierMenu, categoryMenu, operationMenu);

        // 筛选方法
        addMenuItemClickHandler(supplierMenu);
        addMenuItemClickHandler(categoryMenu);

        // 操作商品点击事件
        operation1.setOnAction(event -> {
            addProduct();
        });
        operation2.setOnAction(event -> {
            try {
                updateProduct();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        operation3.setOnAction(event -> {
            delProduct();
        });
        operation4.setOnAction(event -> {
            viewProduct();
        });
        operation5.setOnAction(event -> {
            searchProduct();
        });

        // 创建表格
        table = new TableView();

        // TableView创建列
        TableColumn<Product, String> idColumn = new TableColumn<>("商品ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("product_id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("商品名称");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("product_name"));

        TableColumn<Product, String> descriptionColumn = new TableColumn<>("商品描述");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("product_description"));
        descriptionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Product, String> call(TableColumn<Product, String> param) {
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
                                text.setWrappingWidth(845);
                            }
                            text.setText(item);
                            setGraphic(text);
                        }
                    }
                };
            }
        });

        TableColumn<Product, String> locationColumn = new TableColumn<>("商品位置");
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("product_location"));

        TableColumn<Product, Integer> categoryColumn = new TableColumn<>("商品类别ID");
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category_id"));

        TableColumn<Product, Integer> supplierColumn = new TableColumn<>("商品供应商ID");
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("商品价格");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, Integer> stocksColumn = new TableColumn<>("商品库存");
        stocksColumn.setCellValueFactory(new PropertyValueFactory<>("stocks"));

        refreshTableData();

        table.getColumns().addAll(idColumn, nameColumn, descriptionColumn, locationColumn, categoryColumn, supplierColumn, priceColumn, stocksColumn);

        // 布局管理
        VBox layout = new VBox(10, title, menuBar, table);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));
        layout.setBackground(new Background(new BackgroundFill(Color.web("#DCDCDC"), CornerRadii.EMPTY, Insets.EMPTY)));

        this.getChildren().add(layout);
    }


    // 过滤数据
    private void filterData(String filter) throws SQLException, ClassNotFoundException {
        if (filter.equals("瓦伦丁") || filter.equals("草莓镇") || filter.equals("圣丹尼斯") || filter.equals("罗兹镇")) {
            SearchData.SearchProductsBySupplier(filter, table);
        } else {
            SearchData.SearchProductsByCatagory(filter, table);
        }
    }

    // 增加商品
    private void addProduct() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("添加商品");

        // 设置样式
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
        String buttonHoverStyle = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";

        // 创建控件
        TextField idField = new TextField();
        idField.setPromptText("请输入商品Id");
        HBox idBox = createInfoBox("商品Id:", idField);

        TextField nameField = new TextField();
        nameField.setPromptText("请输入商品名称");
        HBox nameBox = createInfoBox("商品名称:", nameField);

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("请输入商品描述");
        HBox descriptionBox = createInfoBox("商品描述:", descriptionField);

        TextField locationField = new TextField();
        locationField.setPromptText("请输入商品位置");
        HBox locationBox = createInfoBox("商品位置:", locationField);

        TextField categoryIdField = new TextField();
        categoryIdField.setPromptText("请输入商品类别");
        HBox categoryIdBox = createInfoBox("商品类别Id:", categoryIdField);

        TextField supplierIdField = new TextField();
        supplierIdField.setPromptText("请输入商品供应商Id");
        HBox supplierIdBox = createInfoBox("商品供应商Id:", supplierIdField);

        TextField priceField = new TextField();
        priceField.setPromptText("商品价格");
        HBox priceBox = createInfoBox("商品价格:", priceField);

        TextField stockField = new TextField();
        stockField.setPromptText("请输入商品库存");
        HBox stockBox = createInfoBox("商品库存:", stockField);

        Button addButton = new Button("添加");
        addButton.setStyle(buttonStyle);
        addButton.setOnMouseEntered(e -> addButton.setStyle(buttonHoverStyle));
        addButton.setOnMouseExited(e -> addButton.setStyle(buttonStyle));
        addButton.setOnAction(event -> {
            try {
                AddData.addProduct(idField.getText(),nameField.getText(), descriptionField.getText(), locationField.getText(), Integer.parseInt(categoryIdField.getText()), Integer.parseInt(supplierIdField.getText()), Double.parseDouble(priceField.getText()), Integer.parseInt(stockField.getText()));
                MyAlert.alertSet("添加商品","成功","!恭喜您!");
                dialog.close();
                refreshTableData(); // 刷新数据
            } catch (SQLException e) {
                //e.printStackTrace();
                MyAlert.alertSet("添加商品","失败","!请勿添加已存在的数据!");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                MyAlert.alertSet("添加商品","失败","!请输入正确的商品格式!");
            }
        });

        // 布局
        VBox vbox = new VBox(15, idBox, nameBox, descriptionBox, locationBox, categoryIdBox, supplierIdBox, priceBox, stockBox, addButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 场景
        Scene scene = new Scene(vbox, 400, 650);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    // 创建构建信息的盒子
    private HBox createInfoBox(String labelText, TextField textField) {
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
        String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";
        Label label = new Label(labelText);
        label.setStyle(labelStyle);

        textField.setStyle(textFieldStyle);

        HBox infoBox = new HBox(10, label, textField);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");
        return infoBox;
    }
    private HBox createInfoBox(Label label, TextField textField) {
        String boxStyle = "-fx-background-color: #f9f9f9; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);";

        HBox infoBox = new HBox(10, label, textField);
        infoBox.setPadding(new Insets(10));
        infoBox.setStyle(boxStyle);
        return infoBox;
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
    
    // 更改商品
    private void updateProduct() throws SQLException, ClassNotFoundException {
        Product selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            showProductDetailsById(selectedOrder.getProduct_id());
        } else {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("修改商品信息");

            // 设置样式
            String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
            String buttonHoverStyle = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
            String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
            String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";

             // 创建控件
            Label idLabel = new Label("请输入你要修改的商品ID");
            idLabel.setStyle(labelStyle);

            TextField idField = new TextField();
            idField.setStyle(textFieldStyle);

            Button searchButton = new Button("修改");
            searchButton.setStyle(buttonStyle);
            searchButton.setOnMouseEntered(e -> searchButton.setStyle(buttonHoverStyle));
            searchButton.setOnMouseExited(e -> searchButton.setStyle(buttonStyle));
            searchButton.setOnAction(event -> {
                String id = idField.getText();
                try {
                    if (SearchData.SearchProductsById(id)) {
                        showProductDetailsById(id);
                        dialog.close();
                    } else {
                        MyAlert.alertSet("查找","失败","!未找到该商品!");
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    MyAlert.alertSet("修改","失败","!请勿添加相同的商品信息!");
                } catch (NumberFormatException e){
                    MyAlert.alertSet("修改","失败","!请输入正确的商品格式!");
                }
            });

            // 布局
            VBox vbox = new VBox(15, idLabel, idField, searchButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

            // 场景
            Scene scene = new Scene(vbox, 400, 300);
            dialog.setScene(scene);
            dialog.showAndWait();
        }
    }
    
    // 展示商品详情
    private void showProductDetailsById(String productId) throws SQLException, ClassNotFoundException {
        Stage detailDialog = new Stage();
        detailDialog.initModality(Modality.APPLICATION_MODAL);
        detailDialog.setTitle("商品详情");

        // 获取商品信息
        Product product = getProductById(productId);

        // 设置样式
        String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
        String buttonHoverStyle = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
        String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";

        // 创建控件
        Label idLabel = new Label("商品ID:");
        idLabel.setStyle(labelStyle);
        TextField idField = new TextField(product.getProduct_id());
        idField.setEditable(true);
        idField.setStyle(textFieldStyle);
        HBox idBox = createInfoBox(idLabel, idField);

        Label nameLabel = new Label("商品名称:");
        nameLabel.setStyle(labelStyle);
        TextField nameField = new TextField(product.getProduct_name());
        nameField.setStyle(textFieldStyle);
        HBox nameBox = createInfoBox(nameLabel, nameField);

        Label descriptionLabel = new Label("商品描述:");
        descriptionLabel.setStyle(labelStyle);
        TextField descriptionField = new TextField(product.getProduct_description());
        descriptionField.setStyle(textFieldStyle);
        HBox descriptionBox = createInfoBox(descriptionLabel, descriptionField);

        Label locationLabel = new Label("商品位置:");
        locationLabel.setStyle(labelStyle);
        TextField locationField = new TextField(product.getProduct_location());
        locationField.setStyle(textFieldStyle);
        HBox locationBox = createInfoBox(locationLabel, locationField);

        Label categoryIdLabel = new Label("商品类别ID:");
        categoryIdLabel.setStyle(labelStyle);
        TextField categoryIdField = new TextField(String.valueOf(product.getCategory_id()));
        categoryIdField.setStyle(textFieldStyle);
        HBox categoryIdBox = createInfoBox(categoryIdLabel, categoryIdField);

        Label supplierIdLabel = new Label("商品供应商ID:");
        supplierIdLabel.setStyle(labelStyle);
        TextField supplierIdField = new TextField(String.valueOf(product.getSupplier_id()));
        supplierIdField.setStyle(textFieldStyle);
        HBox supplierIdBox = createInfoBox(supplierIdLabel, supplierIdField);

        Label priceLabel = new Label("商品价格:");
        priceLabel.setStyle(labelStyle);
        TextField priceField = new TextField(String.valueOf(product.getPrice()));
        priceField.setStyle(textFieldStyle);
        HBox priceBox = createInfoBox(priceLabel, priceField);

        Label stockLabel = new Label("商品库存:");
        stockLabel.setStyle(labelStyle);
        TextField stockField = new TextField(String.valueOf(product.getStocks()));
        stockField.setStyle(textFieldStyle);
        HBox stockBox = createInfoBox(stockLabel, stockField);

        Button saveButton = new Button("保存");
        saveButton.setStyle(buttonStyle);
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(buttonHoverStyle));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(buttonStyle));
        saveButton.setOnAction(event -> {
            try {
                UpdateData.updateProduct(idField.getText(),nameField.getText()
                        ,descriptionField.getText(),locationField.getText()
                        ,Integer.parseInt(categoryIdField.getText())
                        ,Integer.parseInt(supplierIdField.getText())
                        ,Double.parseDouble(priceField.getText())
                        ,Integer.parseInt(stockField.getText()));
                refreshTableData();; // 刷新数据
                detailDialog.close();
                MyAlert.alertSet("修改","成功","!恭喜您");
            } catch (Exception e) {
                MyAlert.alertSet("修改","失败","!请输入正确的信息格式!");
            }
        });

        // 布局
        VBox vbox = new VBox(15, idBox, nameBox, descriptionBox, locationBox, categoryIdBox, supplierIdBox, priceBox, stockBox, saveButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        // 场景
        Scene scene = new Scene(vbox, 450, 650);
        detailDialog.setScene(scene);
        detailDialog.showAndWait();
    }

    
    // 通过Id获取商品
    private Product getProductById(String productId) throws SQLException, ClassNotFoundException {
        return DataToObjects.dataToProductById(productId);
    }
    
    // 删除商品
    private void delProduct() {
        Product selectedOrder = table.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            // 创建确认删除的警告框
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("确认删除");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("您确定要删除该商品吗？");

            // 添加按钮
            ButtonType buttonTypeYes = new ButtonType("是", ButtonBar.ButtonData.YES);
            ButtonType buttonTypeNo = new ButtonType("否", ButtonBar.ButtonData.NO);
            confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == buttonTypeYes) {
                    try {
                        if (DeleteData.deleteProduct(selectedOrder.getProduct_id())) {
                            MyAlert.alertSet("删除","成功","!恭喜您!");
                            refreshTableData();; // 刷新数据
                        } else {
                            MyAlert.alertSet("查找","失败","!未找到该商品!");
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } else {
            Stage dialog = new Stage();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("删除商品");

            // 设置样式
            String buttonStyle = "-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
            String buttonHoverStyle = "-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;";
            String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
            String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";

            Label idLabel = new Label("请输入你要删除的商品ID");
            idLabel.setStyle(labelStyle);

            TextField idField = new TextField();
            idField.setStyle(textFieldStyle);

            Button deleteButton = new Button("删除");
            deleteButton.setStyle(buttonStyle);
            deleteButton.setOnMouseEntered(e -> deleteButton.setStyle(buttonHoverStyle));
            deleteButton.setOnMouseExited(e -> deleteButton.setStyle(buttonStyle));
            deleteButton.setOnAction(event -> {
                String id = idField.getText();
                try {
                    // 先检查商品是否存在
                    if (!SearchData.SearchProductsById(id)) {
                        MyAlert.alertSet("查找","失败","!未找到该商品!");
                    } else {
                        // 创建确认删除的警告框
                        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                        confirmationAlert.setTitle("确认删除");
                        confirmationAlert.setHeaderText(null);
                        confirmationAlert.setContentText("您确定要删除该商品吗？");

                        // 添加按钮
                        ButtonType buttonTypeYes = new ButtonType("是", ButtonBar.ButtonData.YES);
                        ButtonType buttonTypeNo = new ButtonType("否", ButtonBar.ButtonData.NO);
                        confirmationAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

                        confirmationAlert.showAndWait().ifPresent(response -> {
                            if (response == buttonTypeYes) {
                                try {
                                    if (DeleteData.deleteProduct(id)) {
                                        MyAlert.alertSet("删除","成功","!恭喜您!");
                                        dialog.close();
                                        refreshTableData();; // 刷新数据
                                    } else {
                                        MyAlert.alertSet("查找","失败","!未找到该商品!");
                                    }
                                } catch (SQLException | ClassNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            // 布局
            VBox vbox = new VBox(15, idLabel, idField, deleteButton);
            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.setStyle("-fx-background-color: #ffffff; -fx-padding: 20px; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 5);");

            vbox.setAlignment(Pos.CENTER);
            vbox.setPadding(new Insets(20));
            vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

            Scene scene = new Scene(vbox, 400, 300);
            dialog.setScene(scene);
            dialog.showAndWait();
        }
    }
    
    // 查看商品
    private void viewProduct() {
        String[] categoryNames = new String[]{"0", "服装", "药品及药剂", "杂货及干货"};
        String[] supplierNames = new String[]{"0", "瓦伦丁", "草莓镇", "圣丹尼斯", "罗兹镇"};
        Product selectedProduct = table.getSelectionModel().getSelectedItem();
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
        productDescriptionArea.setStyle("-fx-font-size: 16px; -fx-background-color: #E0F7FA; -fx-border-color: #80DEEA; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); -fx-focus-color: transparent;");

        Label descriptionLabel = new Label("商品描述:");
        descriptionLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #00796B;");
        VBox descriptionBox = new VBox(descriptionLabel, productDescriptionArea);
        descriptionBox.setPadding(new Insets(10));
        descriptionBox.setStyle("-fx-background-color: #E0F7FA; -fx-border-color: #80DEEA; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox vbox = new VBox(15,
            createInfoBox("商品ID:", selectedProduct.getProduct_id(), "#E8F5E9", "#C8E6C9", "#388E3C"),
            createInfoBox("商品名称:", selectedProduct.getProduct_name(), "#E8F5E9", "#C8E6C9", "#388E3C"),
            descriptionBox,
            createInfoBox("商品位置:", selectedProduct.getProduct_location(), "#E3F2FD", "#BBDEFB", "#1976D2"),
            createInfoBox("类别名称:", categoryNames[selectedProduct.getCategory_id()], "#E0F7FA", "#80DEEA", "#00796B"),
            createInfoBox("供应商名称:", supplierNames[selectedProduct.getSupplier_id()], "#E0F7FA", "#80DEEA", "#00796B"),
            createInfoBox("商品价格:", String.valueOf(selectedProduct.getPrice()), "#E3F2FD", "#BBDEFB", "#1976D2"),
            createInfoBox("库存数量:", String.valueOf(selectedProduct.getStocks()), "#E3F2FD", "#BBDEFB", "#1976D2")
        );

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        Scene scene = new Scene(scrollPane, 550, 595);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    private void viewProduct(Product product) {
        String[] categoryNames = new String[]{"0", "服装", "药品及药剂", "杂货及干货"};
        String[] supplierNames = new String[]{"0", "瓦伦丁", "草莓镇", "圣丹尼斯", "罗兹镇"};
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("查看订单");

        // 商品描述使用 TextArea 显示长文本
        TextArea productDescriptionArea = new TextArea(product.getProduct_description());
        productDescriptionArea.setWrapText(true);
        productDescriptionArea.setEditable(false);
        productDescriptionArea.setPrefRowCount(3);
        productDescriptionArea.setPrefWidth(400);
        productDescriptionArea.setStyle("-fx-font-size: 16px; -fx-background-color: #E0F7FA; -fx-border-color: #80DEEA; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2); -fx-focus-color: transparent;");

        Label descriptionLabel = new Label("商品描述:");
        descriptionLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: #00796B;");
        VBox descriptionBox = new VBox(descriptionLabel, productDescriptionArea);
        descriptionBox.setPadding(new Insets(10));
        descriptionBox.setStyle("-fx-background-color: #E0F7FA; -fx-border-color: #80DEEA; -fx-border-radius: 10px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);");

        VBox vbox = new VBox(15,
                createInfoBox("商品ID:", product.getProduct_id(), "#E8F5E9", "#C8E6C9", "#388E3C"),
                createInfoBox("商品名称:", product.getProduct_name(), "#E8F5E9", "#C8E6C9", "#388E3C"),
                descriptionBox,
                createInfoBox("商品位置:", product.getProduct_location(), "#E3F2FD", "#BBDEFB", "#1976D2"),
                createInfoBox("类别名称:", categoryNames[product.getCategory_id()], "#E0F7FA", "#80DEEA", "#00796B"),
                createInfoBox("供应商名称:", supplierNames[product.getSupplier_id()], "#E0F7FA", "#80DEEA", "#00796B"),
                createInfoBox("商品价格:", String.valueOf(product.getPrice()), "#E3F2FD", "#BBDEFB", "#1976D2"),
                createInfoBox("库存数量:", String.valueOf(product.getStocks()), "#E3F2FD", "#BBDEFB", "#1976D2")
        );

        vbox.setAlignment(Pos.TOP_LEFT);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        ScrollPane scrollPane = new ScrollPane(vbox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-padding: 10px;");

        Scene scene = new Scene(scrollPane, 550, 595);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    
    // 搜索商品
    private void searchProduct() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("搜索商品");

        Button searchByNameButton = new Button("根据商品名称搜索");
        searchByNameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchByNameButton.setOnMouseEntered(e -> searchByNameButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByNameButton.setOnMouseExited(e -> searchByNameButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByNameButton.setOnAction(event -> {
            showInputDialog("商品名称");
            dialog.close();
        });

        Button searchByIdButton = new Button("根据商品ID搜索");
        searchByIdButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchByIdButton.setOnMouseEntered(e -> searchByIdButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByIdButton.setOnMouseExited(e -> searchByIdButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchByIdButton.setOnAction(event -> {
            showInputDialog("商品ID");
            dialog.close();
        });

        HBox hbox = new HBox(15, searchByNameButton, searchByIdButton);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(20));
        hbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        Scene scene = new Scene(hbox, 400, 200);
        dialog.setScene(scene);
        dialog.showAndWait();
    }

    @Override
    public void showInputDialog(String searchType) {
        String labelStyle = "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #333333;";
        String textFieldStyle = "-fx-background-color: #ffffff; -fx-border-color: #cccccc; -fx-border-radius: 5px; -fx-padding: 5px; -fx-font-size: 14px;";
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("输入" + searchType);

        Label label = new Label("请输入" + searchType + ":");
        label.setStyle(labelStyle);

        // 获得数据文本框
        TextField textField = new TextField();
        textField.setStyle(textFieldStyle);

        Button searchButton = new Button("搜索");
        searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;");
        searchButton.setOnMouseEntered(e -> searchButton.setStyle("-fx-background-color: #45a049; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchButton.setOnMouseExited(e -> searchButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-border-radius: 5px; -fx-padding: 10px 20px; -fx-cursor: hand;"));
        searchButton.setOnAction(event -> {
            String input = textField.getText();
            try {
                judgeProductData(searchType, input);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            dialog.close();
        });

        VBox vbox = new VBox(15, label, textField, searchButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.setStyle("-fx-background-color: #f0f8ff; -fx-border-color: #b0c4de; -fx-border-radius: 10px; -fx-border-width: 2px;");

        Scene scene = new Scene(vbox, 400, 300);
        dialog.setScene(scene);
        dialog.showAndWait();
    }
    @Override
    public void judgeProductData(String searchType, String input) throws SQLException, ClassNotFoundException {
        try {
            Product product;
            if (searchType.equals("商品ID")) {
                product = DataToObjects.dataToProductById(input);
            } else {
                product = DataToObjects.dataToProductByName(input);
            }
            viewProduct(product);
        } catch (NullPointerException e) {
            Alert alert;
            MyAlert.alertSet("搜索","失败","!未找到该商品!");
        }
    }
    @Override
    public void refreshTableData() {
        try {
            String sql = "SELECT * FROM product";
            Connection conn = MysqlCon.Connection();
            PreparedStatement ppst = conn.prepareStatement(sql);
            ResultSet resultSet = ppst.executeQuery();
            ObservableList<Product> list = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Product product = new Product(resultSet.getString("product_id"),
                        resultSet.getString("product_name"),
                        resultSet.getString("product_description"),
                        resultSet.getString("product_location"),
                        resultSet.getInt("category_id"),
                        resultSet.getInt("supplier_id"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stocks"));
                list.add(product);
            }
            MysqlCon.closeCon(conn,ppst,resultSet);
            table.setItems(list);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}