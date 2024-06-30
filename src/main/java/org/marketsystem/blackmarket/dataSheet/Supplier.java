package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:42
 * @description:
 */
public class Supplier {
    private int supplier_id;
    private String supplier_name;
    private String country;
    private String city;

    public Supplier(int supplier_id, String supplier_name, String country, String city) {
        this.supplier_id = supplier_id;
        this.supplier_name = supplier_name;
        this.country = country;
        this.city = city;
    }

    public int getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(int supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
