/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models.rules;

import com.baolam.traditional_feast_order_management.interfaces.IFeastMenuRule;

/**
 *
 * @author lam01
 */
public class FeastMenuRule implements IFeastMenuRule {
    protected String feastCodeRule = "^PW\\d+$";
    
    public FeastMenuRule() {
    }
    
    @Override
    public boolean checkFeastCode(String feastCode)
    {
        return feastCode.matches(feastCodeRule);
    } 
}
