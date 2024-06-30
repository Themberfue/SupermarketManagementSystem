package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:39
 * @description:
 */
public class Cashier {
    private int cashier_id;
    private String cashier_name;
    private String shift;
    private String phone;
    private String email;
    private String hire_date;
    private String password;

    public Cashier(int cashier_id, String cashier_name, String shift, String phone, String email, String hire_date, String password) {
        this.cashier_id = cashier_id;
        this.cashier_name = cashier_name;
        this.shift = shift;
        this.phone = phone;
        this.email = email;
        this.hire_date = hire_date;
        this.password = password;
    }

    public int getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(int cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getCashier_name() {
        return cashier_name;
    }

    public void setCashier_name(String cashier_name) {
        this.cashier_name = cashier_name;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHire_date() {
        return hire_date;
    }

    public void setHire_date(String hire_date) {
        this.hire_date = hire_date;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
