module org.example.blackmarket {
    requires javafx.controls;
    requires javafx.fxml;

    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    requires java.sql;
    requires mysql.connector.java;

    opens org.marketsystem.blackmarket to javafx.fxml;
    //exports org.marketsystem.blackmarket;
    exports org.marketsystem.blackmarket.utils;
    opens org.marketsystem.blackmarket.utils to javafx.fxml;
    exports org.marketsystem.blackmarket.loginAndRegister;
    opens org.marketsystem.blackmarket.loginAndRegister to javafx.fxml;
    exports org.marketsystem.blackmarket.warehouseManage;
    exports org.marketsystem.blackmarket.cashierManage;
    exports org.marketsystem.blackmarket.dataSheet;
    exports  org.marketsystem.blackmarket.customerInfo.commonCustomerInfo;
    exports org.marketsystem.blackmarket.customerInfo.plusCustomerInfo;

}