/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models.rules;

import com.baolam.traditional_feast_order_management.interfaces.ICustomerRule;

/**
 *
 * @author lam01
 *
 */
public class CustomerRule implements ICustomerRule {
    protected String customerRule = "^[CGK]\\d{4}";
    protected String phoneNumberRule = "^0\\d{9}";
    protected String emailRule = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    protected int minNameLength = 2;
    protected int maxNameLength = 25;
    
    public CustomerRule() {
    }
    
    @Override
    public boolean checkCustomerCode(String code) 
    {
        /// Phải kiểm tra thêm không trùng lắp
        if (code.isEmpty())
            return false;
        return code.matches(customerRule);
    }
    
    @Override
    public boolean checkCustomerName(String name)
    {
        if (name.isEmpty())
            return false;
        return minNameLength <= name.length() && name.length() <= maxNameLength;
    }
    
    @Override
    public boolean checkPhoneNumber(String phone)
    {
        return phone.matches(phoneNumberRule);
    }
    
    @Override
    public boolean checkEmail(String email)
    {
        return email.matches(emailRule);
//        return true;
    }
}
