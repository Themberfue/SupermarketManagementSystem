package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:41
 * @description:
 */
public class OrderDetails {
    private int order_detailId;
    private int order_id;
    private int product_id;
    private int quantity;
    private double unit_price;
    private double discount;

    public OrderDetails(int order_detailId, int order_id, int product_id, int quantity, double unit_price, double discount) {
        this.order_detailId = order_detailId;
        this.order_id = order_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.discount = discount;
    }

    public int getOrder_detailId() {
        return order_detailId;
    }

    public void setOrder_detailId(int order_detailId) {
        this.order_detailId = order_detailId;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
