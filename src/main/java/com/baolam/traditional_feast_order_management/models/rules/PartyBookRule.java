/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models.rules;

import com.baolam.traditional_feast_order_management.interfaces.IPartyBookRule;
import java.util.Date;

/**
 *
 * @author lam01
 */
public class PartyBookRule implements IPartyBookRule {
    private final CustomerRule customerRule = new CustomerRule();
    private final FeastMenuRule feastMenuRule = new FeastMenuRule();
    
    @Override
    public boolean checkCustomerCode(String customerCode) {
        return customerRule.checkCustomerCode(customerCode);
    }

    @Override
    public boolean checkSetMenuCode(String menuCode) {
        return feastMenuRule.checkFeastCode(menuCode);
    }
    
    @Override
    public boolean checkNumberOfTables(int number) {
        return number > 0;
    }

    @Override
    public boolean checkPreferredDate(Date date) {
        Date currentDate = new Date();
        return currentDate.before(date);
    }
}
