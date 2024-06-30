package org.marketsystem.blackmarket.utils;

import javafx.scene.control.Alert;

import java.util.Objects;

/**
 * @ClassDescription:
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/21 11:24
 */
public class MyAlert {
    static Alert alert;
    public static void alertSet(String action,String isFail,String reason){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (Objects.equals(isFail, "成功"))
            alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle(action);
        alert.setHeaderText(null);
        alert.setContentText(action+isFail+reason);
        alert.showAndWait();
    }
}


/**
 * @BelongsProject: BlackMarket
 * @BelongsPackage: org.marketsystem.blackmarket.tools
 * @Author: Pxoolcm
 * @CreateTime: 2024-06-21  11:24
 * @Description: TODO
 * @Version: 1.0
 */
