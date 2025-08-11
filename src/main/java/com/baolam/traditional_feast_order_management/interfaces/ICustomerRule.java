/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.baolam.traditional_feast_order_management.interfaces;

/**
 *
 * @author lam01
 */
public interface ICustomerRule {
    boolean checkCustomerCode(String customerCode);
    boolean checkCustomerName(String name);
    boolean checkPhoneNumber(String phone);
    boolean checkEmail(String email);
}
