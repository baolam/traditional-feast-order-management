/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.baolam.traditional_feast_order_management.interfaces;

import java.util.Date;

/**
 *
 * @author lam01
 */
public interface IPartyBookRule {
    boolean checkCustomerCode(String customerCode);
    boolean checkSetMenuCode(String menuCode);
    boolean checkNumberOfTables(int number);
    boolean checkPreferredDate(Date date);
}
