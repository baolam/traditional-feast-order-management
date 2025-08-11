/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.baolam.traditional_feast_order_management;

import com.baolam.traditional_feast_order_management.controllers.CustomerManagement;
import com.baolam.traditional_feast_order_management.controllers.PartyBookManagement;
import com.baolam.traditional_feast_order_management.models.Customer;
import com.baolam.traditional_feast_order_management.models.PartyBook;
import com.baolam.traditional_feast_order_management.models.rules.CustomerRule;
import com.baolam.traditional_feast_order_management.models.rules.FeastMenuRule;
import com.baolam.traditional_feast_order_management.models.rules.PartyBookRule;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import com.baolam.traditional_feast_order_management.views.CustomerView;
import com.baolam.traditional_feast_order_management.views.MainMenu;
import com.baolam.traditional_feast_order_management.views.PartyBookView;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author lam01
 */
public class Traditional_feast_order_management {
    private static boolean stopProgram = false;
    private static MainMenu menu = null;
    
    private static CustomerView customerView = null;
    private static CustomerManagement customerManagement = null;
    private static CustomerRule customerRule = null;
    
    private static FeastMenuRule feastMenuRule = null;
    
    private static PartyBookRule partyBookRule = null;
    private static PartyBookManagement partyBookManagement = null;
    private static PartyBookView partyBookView = null;
    
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        
        /// Tiến hành gắn kết các đối tượng xử lí
        menu = new MainMenu();
        customerRule = new CustomerRule();
        feastMenuRule = new FeastMenuRule();
        partyBookRule = new PartyBookRule();
        
        customerManagement = new CustomerManagement();
        customerManagement.setCustomerRule(customerRule);
        
        customerView = new CustomerView();
        customerView.setManagement(customerManagement);
        
        partyBookManagement = new PartyBookManagement();
        partyBookManagement.setCustomerManagement(customerManagement);
        partyBookManagement.setFeastMenuRule(feastMenuRule);
        partyBookManagement.setPartyBookRule(partyBookRule);
        
        partyBookView = new PartyBookView();
        partyBookView.setPartyBookmanagement(partyBookManagement);
        partyBookView.setCustomerView(customerView);
        partyBookView.setCustomerManagement(customerManagement);
        /// --------------------------------------------------------------------
        
        while (! stopProgram)
        {
            menu.showOveallPrompt();
            MainMenu.Menu menuCode = (MainMenu.Menu) menu.prompt();
            switch (menuCode)
            {
                case MainMenu.Menu.CODE_FAILED -> {
                    System.out.println("This code does not exist!");
                }
                case MainMenu.Menu.REGISTER_CUSTOMER -> {
                    registerCustomer();
                }
                case MainMenu.Menu.UPDATE_CUSTOMER -> {
                    updateCustomer();
                }
                case MainMenu.Menu.SEARCH_CUSTOMER_BY_NAME -> {
                    searchCustomer();
                }
                case MainMenu.Menu.DISPLAY_FEAST -> {
                    displayFeastMenus();
                }
                case MainMenu.Menu.DISPLAY_CHOICE -> {
                    displayChoice();
                }
                case MainMenu.Menu.PLACE_FEAST_ORDER -> {
                    placeFeastOrder();
                }
                case MainMenu.Menu.UPDATE_ORDER -> {
                    updateOrder();
                }
                case MainMenu.Menu.EXIT_PROGRAM -> {
                    stopProgram = true;
                }
            }
        }
    }
    
    static void registerCustomer()
    {
        boolean isPromptOk = false;
        while (!isPromptOk)
        {
            try {
                Customer customer = customerView.promptInforCustomer(true);
                customerView.showExactCustomer(customer);
                isPromptOk = customerManagement.addNewCustomer(customer);
                if (! isPromptOk)
                {
                    System.out.println("Duplicate code!");
                }
            } catch (RuleExpection e)
            {
                System.out.println(e.getMessage());
            }
        }
        
        /// Kiểm tra trường hợp người dùng muốn nhập tiếp
        System.out.println("Add new customer...");
        if (menu.shouldContinueDoing(false))
        {
            registerCustomer();
        }
    }
    
    static void updateCustomer()
    {
        boolean isPromptedOk = false;
        while (! isPromptedOk)
        {
            isPromptedOk = customerView.promptUpdateCustomer();
            customerView.showUpdateCustomer(isPromptedOk);
        }
        
        System.out.println("Update customer...");
        if (menu.shouldContinueDoing(false))
        {
            updateCustomer();
        }
    }
    
    static void searchCustomer()
    {
        String name = customerView.searchName();
        List<Customer> customers = customerManagement.retrieveCustomerByNames(name);
        customerView.showSearchCustomerByName(customers);
        
        System.out.println("Seach name...");
        if (menu.shouldContinueDoing(false))
            searchCustomer();
    }
    
    static void displayFeastMenus()
    {
        partyBookView.showFeastMenu();
    }
    
    static void placeFeastOrder()
    {
        try {
            PartyBook book = partyBookView.promptPlaceOrder();
            PartyBookManagement.OrderedStatus status = partyBookManagement.addNewOrder(book);
            partyBookView.showPlaceOrder(status);
        } catch (RuleExpection e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("Place feast order...");
        if (menu.shouldContinueDoing(false))
            placeFeastOrder();
    }
    
    static void updateOrder()
    {
        try
        {
            PartyBook book = partyBookView.promptUpdateOrder();
            boolean status = partyBookManagement.updateOrder(book);
            partyBookView.showUpdateOrder(status, book);
        }
        catch (RuleExpection e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("Update order...");
        if (menu.shouldContinueDoing(false))
            updateOrder();
    }
    
    static void displayChoice()
    {
        System.out.println("0. For customers");
        System.out.println("1. For orders");
        System.out.print("Enter code: ");
        boolean code = menu.shouldContinueDoing(true);
        if (!code)
        {
            List<Customer> customers = customerManagement.getCustomers();
            customerView.showAllCustomers(customers);
        }
        else
        {
            System.out.println("Developping!");
        }
    }
}
