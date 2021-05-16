package com.impostors.companyemployee;

import java.util.HashMap;

public class Company {
   private  String name,email,phoneNumber,company_id;
   private HashMap<String,Employee> EmployeeHash;


    public Company(String name, String email, String phoneNumber, String company_id) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public HashMap<String, Employee> getEmployeeHash() {
        return EmployeeHash;
    }

    public void setEmployeeHash(HashMap<String, Employee> employeeHash) {
        EmployeeHash = employeeHash;
    }
}
