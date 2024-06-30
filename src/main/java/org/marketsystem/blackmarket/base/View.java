package org.marketsystem.blackmarket.base;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.SQLException;

public interface View {
    /**
     *
     * @param searchType
     */
    void showInputDialog(String searchType);
    /**
     *
     * @param searchType
     * @param input
     */
    void judgeProductData(String searchType, String input) throws SQLException, ClassNotFoundException;

    /**
     *
     */
    void refreshTableData();

    /**
     *
     * @param path
     * @param width
     * @param height
     * @return
     */
     default ImageView createImageView(String path, int width, int height) {
         ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(path)));
         imageView.setFitWidth(width);
         imageView.setFitHeight(height);
         return imageView;
     }
}
