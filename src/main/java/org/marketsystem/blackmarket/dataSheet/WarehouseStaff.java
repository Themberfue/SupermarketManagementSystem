package org.marketsystem.blackmarket.dataSheet;

/**
 * @author: Themberfue
 * @date: 2024/6/20 19:42
 * @description:
 */
public class WarehouseStaff {
    private int staff_id;
    private String staff_name;
    private String phone;
    private String email;
    private String hire_date;
    private String password;

    public WarehouseStaff(int staff_id, String staff_name, String phone, String email, String hire_date, String password) {
        this.staff_id = staff_id;
        this.staff_name = staff_name;
        this.phone = phone;
        this.email = email;
        this.hire_date = hire_date;
        this.password = password;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
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
