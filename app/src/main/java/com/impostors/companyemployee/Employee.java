package com.impostors.companyemployee;

public class Employee {
    String name;
    String phoneNumber;
    String position;
    String email;
    String ssn;

    public Employee(String name, String phoneNumber, String role, String email,String ssn) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.position = role;
        this.email = email;
        this.ssn=ssn;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
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

    public String getRole() {
        return position;
    }

    public void setRole(String role) {
        this.position = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
