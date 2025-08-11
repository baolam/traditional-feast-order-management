/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models;

import com.baolam.traditional_feast_order_management.models.rules.CustomerRule;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import java.util.Objects;

/**
 *
 * @author lam01
 */
public class Customer implements Comparable<Customer> {
    private String customerCode;
    private String name;
    private String phoneNumber;
    private String email;
    
    private final CustomerRule protector = new CustomerRule();

    public Customer(String customerCode, String name, String phoneNumber, String email) {
        this.customerCode = customerCode;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Customer() {
    }
    
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) throws RuleExpection {
        if (!protector.checkCustomerCode(customerCode))
            throw new RuleExpection(customerCode + " violates rule!");
         this.customerCode = customerCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws RuleExpection {
        if (!protector.checkCustomerName(name))
            throw new RuleExpection(name + " violates rule!");
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws RuleExpection {
        if (! protector.checkPhoneNumber(phoneNumber))
            throw new RuleExpection(phoneNumber + " violates rule!");
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws RuleExpection {
        if (! protector.checkEmail(email))
            throw new RuleExpection(email + " violates rule!");
        this.email = email;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.customerCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        return Objects.equals(this.customerCode, other.customerCode);
    }

    @Override
    public int compareTo(Customer o) {
        return this.name.compareTo(o.getName());
    }
}
