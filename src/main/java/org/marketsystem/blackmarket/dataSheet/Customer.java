package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:39
 * @description:
 */
public class Customer {
    private int customer_id;
    private String customer_name;
    private String phone;
    private String email;
    private String address;
    private boolean is_vip;
    private String is_plus;
    private String password;

    public String getIs_plus() {
        return is_plus;
    }

    public void setIs_plus(String is_plus) {
        this.is_plus = is_plus;
    }

    public Customer(int customer_id, String customer_name, String phone, String email, String address, String is_plus) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.is_plus = is_plus;
    }
    public Customer(int customer_id, String customer_name, String phone, String email, String address, boolean is_vip,String pwd) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.is_vip = is_vip;
        this.password = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isIs_vip() {
        return is_vip;
    }

    public void setIs_vip(boolean is_vip) {
        this.is_vip = is_vip;
    }
}
