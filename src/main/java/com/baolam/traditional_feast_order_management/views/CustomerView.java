package com.baolam.traditional_feast_order_management.views;

import com.baolam.traditional_feast_order_management.controllers.CustomerManagement;
import com.baolam.traditional_feast_order_management.models.Customer;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.List;
import java.util.Scanner;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author lam01
 */
public class CustomerView {
    private CustomerManagement _management = null;
    private Scanner scanner = null;
    
    public CustomerView() {
        scanner = new Scanner(System.in);
    }

    public void setManagement(CustomerManagement _management) {
        this._management = _management;
    }
    
    public void showUpdateCustomer(boolean status)
    {
        if (!status)
            System.out.println("This customer does not exist!");
    }
    
    public void showSearchCustomerByName(List<Customer> customers)
    {
        if (customers.isEmpty())
        {
            System.out.println("No one matches the search criteria!");
            return;
        }
        this.showAllCustomers(customers);
    }
    
    public void showAllCustomers(List<Customer> customers)
    {        
        AsciiTable rawCustomer = new AsciiTable();
        rawCustomer.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        
//        rawCustomer.addRow("List of customers").setTextAlignment(TextAlignment.CENTER);
        rawCustomer.addRule();
        rawCustomer.addRow("Code", "Customer Name", "Phone", "Email");
        rawCustomer.addRule();
        
        for (Customer c : customers)
        {
            rawCustomer.addRow(c.getCustomerCode(), c.getName(), c.getPhoneNumber(), c.getEmail());
        }
  
        rawCustomer.addRule();
        
        String render = rawCustomer.render();
        System.out.println(render);
    }
    
    public void showExactCustomer(Customer c)
    {
        AsciiTable rawCustomer = new AsciiTable();
        rawCustomer.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        
        rawCustomer.addRow("Show detail customer").setTextAlignment(TextAlignment.CENTER);
        rawCustomer.addRule();
        this.showExactCustomer(c, rawCustomer);
        rawCustomer.addRule();
        String render = rawCustomer.render();
        System.out.println(render);
    }    
    
    public void showExactCustomer(Customer c, AsciiTable rawCustomer)
    {
        rawCustomer.addRow("Code: " + c.getCustomerCode());
        rawCustomer.addRow("Name: " + c.getName());
        rawCustomer.addRow("Phone: " + c.getPhoneNumber());
        rawCustomer.addRow("Email: " + c.getEmail());
    }
    
    public void showSaveData(boolean status)
    {
        if (status)
        {
            System.out.println("Customer data has been successfully saved to '(%s)'".formatted(_management.getStorageName()));
        }
        else 
        {
            System.out.println("Customer data has been failed to save to '(%s)'".formatted(_management.getStorageName()));
        }
    }
    
    public Customer promptInforCustomer(boolean isDuplicated) throws RuleExpection
    {
        Customer customer = new Customer();
        
        String customerCode = "";
        do {
            System.out.print("Enter customer code: ");
            customerCode = scanner.nextLine();
        } while (!_management.getCustomerRule().checkCustomerCode(customerCode));
        
        if (isDuplicated && _management.retrieveCustomerByCode(customerCode) != null)
            throw new RuleExpection("CustomerCode " + customerCode + " existed!");
        
        if (!isDuplicated)
        {
            customer = _management.retrieveCustomerByCode(customerCode);
            if (customer == null)
                throw new RuleExpection("CustomerCOde " + customerCode + " does not exist!");
        }
        
        customer.setCustomerCode(customerCode);
        
        if (isDuplicated)
        {
            customer.setName(this.promptCustomerName(false));
            customer.setPhoneNumber(this.promptCustomerPhone(false));
            customer.setEmail(this.promptCustomerEmail(false));
        }
        else
        {
            System.out.println("Skip name?(0/1): " + customer.getName());
            System.out.print("Skip:");
            if (this.getSpecialCode() == 0)
                customer.setName(this.promptCustomerName(true));
            
            System.out.println("Skip phone?(0/1): " + customer.getPhoneNumber());
            System.out.print("Skip:");
            if (this.getSpecialCode() == 0)
                customer.setPhoneNumber(this.promptCustomerPhone(true));
            
            System.out.println("Skip email?(0/1): " + customer.getEmail());
            System.out.print("Skip:");
            if (this.getSpecialCode() == 0)
                customer.setEmail(this.promptCustomerEmail(true));
        }
        
        return customer;
    }
    
    public boolean promptUpdateCustomer()
    {
        boolean status;
        try {
            /// Lấy hàm có sẵn
            Customer c = this.promptInforCustomer(false);
            status = this._management.updateCustomer(c);
        } catch (RuleExpection e)
        {
            status = false;
        }
        return status;
    }
  
    public String searchName()
    {
        System.out.print("Enter name: ");
        return scanner.nextLine();
    }
    
    private int getSpecialCode()
    {
        int code;
        do {
            code = scanner.nextInt();
        } while (code < 0 || code > 1);
        return code;
    }
    
    private String promptCustomerName()
    {
        String name = "";
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
        } while (! _management.getCustomerRule().checkCustomerName(name));
        return name;
    }
    
    private String promptCustomerName(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptCustomerName();
    }
    
    private String promptCustomerPhone()
    {
        String phone = "";
        do {
            System.out.print("Enter phone: ");
            phone = scanner.nextLine();
        } while (! _management.getCustomerRule().checkPhoneNumber(phone));
        return phone;
    }
    
    private String promptCustomerPhone(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptCustomerPhone();
    }
    
    private String promptCustomerEmail()
    {
        String mail = "";
        do {
            System.out.print("Enter mail: ");
            mail = scanner.nextLine();
        } while (! _management.getCustomerRule().checkEmail(mail));
        return mail;
    }
    
    private String promptCustomerEmail(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptCustomerEmail();
    }
}
