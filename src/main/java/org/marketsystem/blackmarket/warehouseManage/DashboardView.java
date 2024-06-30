package org.marketsystem.blackmarket.warehouseManage;

/**
 * @author: Themberfue
 * @date: 2024/6/19 21:14
 * @description:
 */
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DashboardView extends StackPane {

    public DashboardView() {
        // 创建标题
        Label title = new Label("仪表盘");
        title.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD, 28));

        // 创建散点图
        NumberAxis xAxis = new NumberAxis(2013, 2024, 1);
        NumberAxis yAxis = new NumberAxis(0, 3, 0.5);
        ScatterChart<Number, Number> scatterChart = new ScatterChart<>(xAxis, yAxis);

        xAxis.setLabel("年份");
        yAxis.setLabel("数量");

        XYChart.Series<Number, Number> series1 = new XYChart.Series<>();
        series1.setName("赌徒装束");

        XYChart.Series<Number, Number> series2 = new XYChart.Series<>();
        series2.setName("牛仔装束");

        // 使用 for 循环添加数据点
        for (int year = 2014; year <= 2023; year++) {
            series1.getData().add(new XYChart.Data<>(year, Math.random() * 3));
            series2.getData().add(new XYChart.Data<>(year, Math.random() * 3));
        }

        scatterChart.getData().addAll(series1, series2);

        // 布局管理
        VBox layout = new VBox(10, title, scatterChart);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        this.getChildren().add(layout);

        // 这里可以添加更多的布局和组件
    }

    // 创建一个静态内部类作为表格的数据模型
    public static class Item {
        private final String name;
        private final int quantity;

        public Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
