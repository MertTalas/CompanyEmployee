package com.impostors.companyemployee;

public class Employee {
    String name;
    String phoneNumber;
    String position;
    String email;
    String company_id;

    public Employee() {
    }

    public Employee(String name, String phoneNumber, String position, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = position;
        this.email = email;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
