/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.controllers;

import com.baolam.traditional_feast_order_management.models.Customer;
import com.baolam.traditional_feast_order_management.models.rules.CustomerRule;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author lam01
 */
public class CustomerManagement {
    private final List<Customer> customers = new ArrayList();
    private CustomerRule customerRule = null;
    
    /***
     * 
     * @param code
     * @param name
     * @param phone
     * @param email
     * @exception 
     * Trả về việc thêm người dùng mới có thành công hay không?
     * @return 
     */
    public boolean addNewCustomer(String code, String name, String phone, String email)
    {
        boolean isSatisfied = customerRule.checkCustomerCode(code) && customerRule.checkCustomerName(name) && customerRule.checkEmail(email) && customerRule.checkPhoneNumber(phone);
        if (!isSatisfied)
            return false;

        Customer newCustomer = new Customer(code, name, phone, email);
        if (customers.contains(newCustomer))
            return false;
        customers.add(newCustomer);
        return true;        
    }
    
    public boolean addNewCustomer(Customer c)
    {
        return this.addNewCustomer(c.getCustomerCode(), c.getName(), c.getPhoneNumber(), c.getEmail());
    }
    
    /***
     * 
     * @param code
     * @exception 
     * Tìm kiếm người dùng bằng mã định danh (customerCode)
     * @return 
     */
    public Customer retrieveCustomerByCode(String code)
    {
        if (!customerRule.checkCustomerCode(code))
            return null;
        Customer foundCustomer = null;
        for (Customer customer : customers)
        {
            if (customer.getCustomerCode().equals(code))
            {
                foundCustomer = customer;
                break;
            }
        }
        return foundCustomer;
    }
    
    public List<Customer> retrieveCustomerByNames(String name)
    {
        List<Customer> retrievedCustomers = new ArrayList();
        if (! customerRule.checkCustomerName(name))
            return retrievedCustomers;
        
        for (Customer customer : customers)
        {
            if (customer.getName().toLowerCase().contains(name.toLowerCase()))
            {
                retrievedCustomers.add(customer);
            }
        }
        
        /// Sorting by name
        if (retrievedCustomers.size() >= 2)
        {
            Collections.sort(retrievedCustomers);
        }
        
        return retrievedCustomers;
    }
    
    /***
     * 
     * @param code
     * @param name
     * @param phone
     * @param email
     * @exception 
     * Cập nhật thông tin người dùng dựa trên customerCode
     * @return 
     */
    public boolean updateCustomer(String code, String name, String phone, String email)
    {
        Customer customer = this.retrieveCustomerByCode(code);
        if (customer == null)
            return false;
        
        boolean status = true;
        
        try {
            customer.setName(name);
            customer.setPhoneNumber(phone);
            customer.setEmail(email);
        } catch (RuleExpection e) {
            status = false;
        }
        
        return status;
    }
    
    public boolean updateCustomer(Customer c)
    {
        return this.updateCustomer(c.getCustomerCode(), c.getName(), c.getPhoneNumber(), c.getEmail());
    }
    
    public CustomerRule getCustomerRule() {
        return customerRule;
    }

    public void setCustomerRule(CustomerRule customerRule) {
        this.customerRule = customerRule;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
