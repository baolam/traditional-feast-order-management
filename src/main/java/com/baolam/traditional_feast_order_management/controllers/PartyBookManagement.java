/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.controllers;

import com.baolam.traditional_feast_order_management.interfaces.ISaveLoad;
import com.baolam.traditional_feast_order_management.models.FeastMenu;
import com.baolam.traditional_feast_order_management.models.PartyBook;
import com.baolam.traditional_feast_order_management.models.rules.PartyBookRule;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lam01
 */
public class PartyBookManagement extends FeastManagement implements ISaveLoad {    
    /// Mã trạng thái Order.
    public enum OrderedStatus {
        ORDERED_FAILED, ORDERED_SUCCESS, ORDERED_DUPLICATE
    };
    
    private String storageName = "feast_order_service.dat";
    private final ArrayList<PartyBook> partyBooks = new ArrayList();
    private int totalIds = 0;
    
    private CustomerManagement customerManagement = null;
    private PartyBookRule partyBookRule = null;
    
    public PartyBookManagement() {
        super();
    }

    public PartyBookRule getPartyBookRule() {
        return partyBookRule;
    }

    public ArrayList<PartyBook> getPartyBooks() {
        return partyBooks;
    }

    public void setPartyBookRule(PartyBookRule partyBookRule) {
        this.partyBookRule = partyBookRule;
    }
    
    public void setCustomerManagement(CustomerManagement customerManagement) {
        this.customerManagement = customerManagement;
    }
    
    /***
     * 
     * @param customerCode
     * @exception 
     * Trả về Order theo khách hàng
     * @return 
     */
    public ArrayList<PartyBook> retrievePartyByCustomerCode(String customerCode)
    {
        ArrayList<PartyBook> result = new ArrayList();
        
        for (PartyBook book : partyBooks)
        {
            if (book.getCustomerCode().equals(customerCode))
                result.add(book);
        }
        
        return result;
    }
    
    public boolean checkExistOrder(int tableId)
    {
        return 1 <= tableId && tableId <= this.totalIds;
    }
    
    public boolean checkDuplicateOrder(PartyBook book)
    {
        ArrayList<PartyBook> orderedByCustomer = this.retrievePartyByCustomerCode(book.getCustomerCode());
        if (orderedByCustomer.isEmpty())
            return false;
        for (PartyBook ordered : orderedByCustomer)
        {
            if (ordered.getSetMenuCode().equals(book.getSetMenuCode()) && ordered.getPreferredDate().equals(book.getPreferredDate()))
                return true;
        }
        return false;
    }
    
    /**
     * 
     * @param customerCode
     * @exception 
     * Trả về tổng chi phí chi trả ứng với một khách hàng
     * @return 
     */
    public int getCost(String customerCode)
    {
        ArrayList<PartyBook> orders = this.retrievePartyByCustomerCode(customerCode);
        
        int total = 0;
        
        for (PartyBook order : orders)
        {
            FeastMenu menu = this.retrieveMenuByCode(order.getSetMenuCode());
            if (menu == null)
                continue;
            total += order.getNumberOfTables() * menu.getPrice();
        }
        
        return total;
    }
    
    /***
     * 
     * @param customerCode
     * @param setMenuCode
     * @param number
     * @param date
     * @exception 
     * Thêm một Order vào quản lí, trả về trạng thái để hiển thị
     * @return 
     */
    public OrderedStatus addNewOrder(String customerCode, String setMenuCode, int number, Date date)
    {
        boolean registeredCustomer = customerManagement.retrieveCustomerByCode(customerCode) != null;
        if (! registeredCustomer)
            return OrderedStatus.ORDERED_FAILED;
        
        boolean providedMenu = this.retrieveMenuByCode(setMenuCode) != null;
        if (! providedMenu)
            return OrderedStatus.ORDERED_FAILED;
        
        OrderedStatus status = OrderedStatus.ORDERED_SUCCESS;
        try {
            PartyBook book = new PartyBook();
            
            book.setCustomerCode(customerCode);
            book.setSetMenuCode(setMenuCode);
            book.setNumberOfTables(number);
            book.setPreferredDate(date);
            
            this.totalIds++;
            book.setBookId(this.totalIds);
            
            if (this.checkDuplicateOrder(book))
                status = OrderedStatus.ORDERED_DUPLICATE;
            else partyBooks.add(book);
        } catch (RuleExpection e)
        {
            status = OrderedStatus.ORDERED_FAILED;
        }
        
        return status;
    }
    
    public OrderedStatus addNewOrder(PartyBook book)
    {
        return this.addNewOrder(book.getCustomerCode(), book.getSetMenuCode(), book.getNumberOfTables(), book.getPreferredDate());
    }
    
    /***
     * 
     * @param orderId
     * @param menuCode
     * @param numTables
     * @param preferredDate
     * @exception 
     * Cập nhật Order có thành công hay không
     * @return 
     */
    public boolean updateOrder(int orderId, String menuCode, int numTables, Date preferredDate)
    {
        if (! this.checkExistOrder(orderId))
            return false;
        
        boolean status = true;
        try {
            PartyBook book = partyBooks.get(orderId - 1);
            book.setSetMenuCode(menuCode);
            book.setNumberOfTables(numTables);
            book.setPreferredDate(preferredDate);
        } catch(RuleExpection e)
        {
            status = false;
        }
        
        return status;
    }
    
    public boolean updateOrder(PartyBook book)
    {
        return this.updateOrder(book.getBookId(), book.getSetMenuCode(), book.getNumberOfTables(), book.getPreferredDate());
    }
    
    public PartyBook retrieveBookById(int id)
    {
        return partyBooks.get(id - 1);
    }
    
    public PartyBook getLatest()
    {
        return partyBooks.get(totalIds - 1);
    }
    
    @Override
    public boolean storeData() {
        boolean status = true;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storageName)))
        {
            oos.writeObject(partyBooks);
        } catch (IOException e)
        {
            status = false;
        }
        return status;
    }

    @Override
    public boolean loadData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getStorageName() {
        return storageName;
    }
}
