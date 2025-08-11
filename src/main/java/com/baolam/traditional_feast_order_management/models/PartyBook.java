/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models;

import com.baolam.traditional_feast_order_management.models.rules.PartyBookRule;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import java.util.Date;

/**
 *
 * @author lam01
 */
public class PartyBook {
    private int bookId;
    private String customerCode;
    private String setMenuCode;
    private int numberOfTables;
    private Date preferredDate;
    
    private final PartyBookRule partyBookRule = new PartyBookRule();
    
    public PartyBook() {
    }

    public PartyBook(int bookId, String customerCode, String setMenuCode, int numberOfTables, Date preferredDate) {
        this.bookId = bookId;
        this.customerCode = customerCode;
        this.setMenuCode = setMenuCode;
        this.numberOfTables = numberOfTables;
        this.preferredDate = preferredDate;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) throws RuleExpection {
        if (! partyBookRule.checkCustomerCode(customerCode))
            throw new RuleExpection(customerCode + " violates rule!");
        this.customerCode = customerCode;
    }

    public String getSetMenuCode() {
        return setMenuCode;
    }

    public void setSetMenuCode(String setMenuCode) throws RuleExpection {
        if (! partyBookRule.checkSetMenuCode(setMenuCode))
            throw new RuleExpection(setMenuCode + " violates rule!");
        this.setMenuCode = setMenuCode;
    }

    public int getNumberOfTables() {
        return numberOfTables;
    }

    public void setNumberOfTables(int numberOfTables) throws RuleExpection {
        if (! partyBookRule.checkNumberOfTables(numberOfTables))
            throw new RuleExpection(numberOfTables + " violates rule!");
        this.numberOfTables = numberOfTables;
    }

    public Date getPreferredDate() {
        return preferredDate;
    }

    public void setPreferredDate(Date preferredDate) throws RuleExpection {
        if (! partyBookRule.checkPreferredDate(preferredDate))
            throw new RuleExpection("date violates rule!");
        this.preferredDate = preferredDate;
    }
}
