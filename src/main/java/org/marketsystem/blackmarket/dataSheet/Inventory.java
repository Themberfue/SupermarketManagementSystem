package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:41
 * @description:
 */
public class Inventory {
    private int inventory_id;
    private String product_id;
    private int quantity;

    public Inventory(int inventory_id, String product_id, int quantity) {
        this.inventory_id = inventory_id;
        this.product_id = product_id;
        this.quantity = quantity;
    }

    public int getInventory_id() {
        return inventory_id;
    }

    public void setInventory_id(int inventory_id) {
        this.inventory_id = inventory_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
