package org.marketsystem.blackmarket.dataSheet;

/**
 * @ClassDescription:
 * @JdkVersion: 2.1
 * @Author: 廖春花
 * @Created: 2024/6/21 9:55
 */
public class Order {
    private int order_id;
    private int customer_id;
    private String customer_name;
    private String order_date;
    private double money;
    private boolean status;


    public Order(int order_id, int customer_id, String customer_name, String order_date, double money, boolean status) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.order_date = order_date;
        this.money = money;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Order(int order_id, int customer_id, String customer_name, String order_date, double money) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.order_date = order_date;
        this.money = money;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
/**
 * @BelongsProject: BlackMarket
 * @BelongsPackage: org.marketsystem.blackmarket.dataSheet
 * @Author: Pxoolcm
 * @CreateTime: 2024-06-21  09:55
 * @Description: TODO
 * @Version: 1.0
 */
