package org.marketsystem.blackmarket.dataSheet;

public class Product {
    private String product_id;
    private String product_name;
    private String product_description;
    private String product_location;
    private int category_id;
    private int supplier_id;
    private double price;
    private int stocks;
    private int quantity;

    public Product(String product_id, String product_name, String product_description, double price, int stocks) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.price = price;
        this.stocks = stocks;
    }

    public Product(String product_id, String product_name, String product_description, String product_location, int category_id, int supplier_id, double price, int stocks) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.product_location = product_location;
        this.category_id = category_id;
        this.supplier_id = supplier_id;
        this.price = price;
        this.stocks = stocks;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getProduct_location() {
        return product_location;
    }

    public void setProduct_location(String product_location) {
        this.product_location = product_location;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStocks() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks = stocks;
    }
}
