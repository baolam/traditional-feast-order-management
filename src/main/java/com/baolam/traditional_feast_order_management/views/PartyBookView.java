/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.views;

import com.baolam.traditional_feast_order_management.controllers.CustomerManagement;
import com.baolam.traditional_feast_order_management.controllers.PartyBookManagement;
import com.baolam.traditional_feast_order_management.models.Customer;
import com.baolam.traditional_feast_order_management.models.FeastMenu;
import com.baolam.traditional_feast_order_management.models.PartyBook;
import com.baolam.traditional_feast_order_management.models.rules.RuleExpection;
import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author lam01
 */
public class PartyBookView {
    private SimpleDateFormat formattedDate = null;
    private PartyBookManagement _management = null;
    private Scanner scanner = null;
    private CustomerManagement _customerManagement = null;
    private CustomerView _customerView = null;
    private NumberFormat nf = null;
    
    public PartyBookView() {
        scanner = new Scanner(System.in);
        formattedDate = new SimpleDateFormat("dd/MM/yyyy");
        nf = NumberFormat.getInstance(new Locale("vi", "VN"));
    }

    public void setPartyBookmanagement(PartyBookManagement _partyBookmanagement) {
        this._management = _partyBookmanagement;
    }
    
    public void setCustomerView(CustomerView _customerView) {
        this._customerView = _customerView;
    }

    public void setCustomerManagement(CustomerManagement _customerManagement) {
        this._customerManagement = _customerManagement;
    }    
    
    public void showFeasts(List<FeastMenu> menus)
    {
        AsciiTable at = new AsciiTable();
        at.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        
        at.addRule();
        at.addRow("List of Set Menus for ordering party:").setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        for (FeastMenu menu : menus)
        {
            this.showExactFeast(menu, at);
        }
        if (menus.isEmpty())
        {
            at.addRow("No data.");
        }
        at.addRule();
        
        String render = at.render();
        System.out.println(render);
    }
    
    public void showExactFeast(FeastMenu menu, AsciiTable at)
    {
        at.addRow("Code       : " + menu.getMenuCode());
        at.addRow("Name       : " + menu.getName());
        at.addRow("Price      : " + nf.format(menu.getPrice()) + " Vnd");
        at.addRow("Ingredients:");
        at.addRow("+ " + menu.getStarter());
        at.addRow("+ " + menu.getMainCourse());
        at.addRow("+ " + menu.getDessert());
    }
    
    public void showFeastMenu()
    {
        if (_management.isFileNotFound())
        {
            System.out.println("Cannot read data from feastMenu.csv. Please check it.");
            return;
        }
        
        showFeasts(_management.getFeastMenus());
    }
    
    public void showExactOrder(PartyBook book)
    {        
        AsciiTable at = new AsciiTable();
        at.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        
        at.addRule();
        at.addRow("Customer order information [ORDER ID: " + book.getBookId() + "]");
        at.addRule();
        
        Customer c = _customerManagement.retrieveCustomerByCode(book.getCustomerCode());
        
        _customerView.showExactCustomer(c, at);
        at.addRule();
        this.showPartyBook(book, at);
        at.addRule();
        
        System.out.println(at.render());
    }
    
    public void showPartyBook(PartyBook book, AsciiTable at)
    {
       FeastMenu menu = this._management.retrieveMenuByCode(book.getSetMenuCode());
       at.addRow("Code of Set Menu: " + book.getSetMenuCode());
       at.addRow("Set menu name: " + menu.getName());
       at.addRow("Event date: " + formattedDate.format(book.getPreferredDate()));
       at.addRow("Number of tables: " + book.getNumberOfTables());
       at.addRow("Price: " + nf.format(menu.getPrice()) + " Vnd");
       at.addRow("Ingredients");
       at.addRow("+ " + menu.getStarter());
       at.addRow("+ " + menu.getMainCourse());
       at.addRow("+ " + menu.getDessert());
       at.addRule();
       at.addRow("Total cost: " + nf.format(book.getNumberOfTables() * menu.getPrice()) + " Vnd");
    }
    
    public void showPlaceOrder(PartyBookManagement.OrderedStatus status)
    {
        if (status == PartyBookManagement.OrderedStatus.ORDERED_DUPLICATE)
        {
            System.out.println("Duplicate data!");
            return;
        }
        if (status == PartyBookManagement.OrderedStatus.ORDERED_FAILED)
        {
            System.out.println("Failed to order (may be wrong input!)");
            return;
        }
        if (status == PartyBookManagement.OrderedStatus.ORDERED_SUCCESS)
        {
            this.showExactOrder(_management.getLatest());
        }
    }
    
    public void showUpdateOrder(boolean status, PartyBook book)
    {
        if (status) 
        {
            this.showExactOrder(book);
        }
        else
        {
            System.out.println("Unexpected error!");
        }
    }
    
    public void showOrder(List<PartyBook> books)
    {
        if (books.isEmpty())
        {
            System.out.println("No data in the system");
        }
        else
        {
            AsciiTable at = new AsciiTable();
            at.getContext().setGrid(A7_Grids.minusBarPlusEquals());
            
            at.addRule();
            at.addRow("ID", "Event date", "Customer ID", "Set Menu", "Price", "Tables", "Cost");
            at.addRule();
            for (PartyBook book : books)
            {
                this.showExactOrder(book, at);
            }
            at.addRule();
            
            String render = at.render();
            System.out.println(render);
        }    
    }
    
    private void showExactOrder(PartyBook book, AsciiTable at)
    {
        FeastMenu menu = _management.retrieveMenuByCode(book.getSetMenuCode());
        int totalCost = menu.getPrice() * book.getNumberOfTables();
        at.addRow(book.getCustomerCode() + " (%d)".formatted(book.getBookId()), 
            formattedDate.format(book.getPreferredDate()), book.getCustomerCode(), book.getSetMenuCode(), 
            nf.format(menu.getPrice()), book.getNumberOfTables(), nf.format(totalCost));
    }
        
    public void showSaveData(boolean status)
    {
        if (status)
        {
            System.out.println("Order data has been successfully saved to '(%s)'".formatted(_management.getStorageName()));
        }
        else 
        {
            System.out.println("Order data has been failed to save to '(%s)'".formatted(_management.getStorageName()));
        }
    }
    
    public PartyBook promptPlaceOrder() throws RuleExpection
    {
        PartyBook book = new PartyBook();
        
        String customerCode = "";
        do
        {
            System.out.print("Enter customer code (make sure satisfied rule and exist): ");
            customerCode = scanner.nextLine();
        } while (! _management.getPartyBookRule().checkCustomerCode(customerCode) 
            || _customerManagement.retrieveCustomerByCode(customerCode) == null);
        book.setCustomerCode(customerCode);
        
        book.setSetMenuCode(this.promptMenuCode(false));
        book.setNumberOfTables(this.promptNumTable(false));
        
        Date preferredDate = this.promptPreferredDate(true);
        book.setPreferredDate(preferredDate);

        return book;
    }
    
    public PartyBook promptUpdateOrder() throws RuleExpection
    {
        PartyBook book = _management.retrieveBookById(this.promptOderId());
        if (book == null)
            throw new RuleExpection("This Order does not exist!");
        
        System.out.println("Skip MenuCode(0/1)? " + book.getSetMenuCode());
        System.out.print("Skip:");
        if (this.getSpecialCode() == 0)
            book.setSetMenuCode(this.promptMenuCode(true));
        
        System.out.println("Skip Number of tables(0/1)? " + book.getNumberOfTables());
        System.out.print("Skip:");
        if (this.getSpecialCode() == 0)
            book.setNumberOfTables(this.promptNumTable(true));
        
        System.out.println("Skip preferredDate(0/1)? " + formattedDate.format(book.getPreferredDate()));
        System.out.print("Skip:");
        if (this.getSpecialCode() == 0)
            book.setPreferredDate(this.promptPreferredDate(true));
        
        return book;
    }
    
        private String promptMenuCode()
    {
        String setMenuCode = "";
        do
        {
            System.out.print("Enter set menu code (make sure satisfied rule and exist): ");
            setMenuCode = scanner.nextLine();
        } while (! _management.getPartyBookRule().checkSetMenuCode(setMenuCode) 
            || _management.retrieveMenuByCode(setMenuCode) == null);
        return setMenuCode;
    }
    
    private String promptMenuCode(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptMenuCode();
    }
    
    private int promptNumTable()
    {
        int numTables = 0;
        do
        {
            System.out.print("Enter number of tables: ");
            numTables = scanner.nextInt();
        } while (! _management.getPartyBookRule().checkNumberOfTables(numTables));
        return numTables;
    }
    
    private int promptNumTable(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptNumTable();
    }
    
    private Date promptPreferredDate()
    {
        Date date = new Date();
        String rawDate = formattedDate.format(date);
        boolean status;
        try {
            do
            {
                System.out.print("Enter preferred date (dd/MM/yyyy):");
                rawDate = scanner.nextLine();
            } while (! _management.getPartyBookRule().checkPreferredDate(formattedDate.parse(rawDate)));

            date = formattedDate.parse(rawDate);
            status = true;
        } catch (ParseException ex) {
            status = false;
        }
        
        if (!status)
            return this.promptPreferredDate(false);
        
        return date;
    }
    
    private Date promptPreferredDate(boolean flush)
    {
        if (flush) scanner.nextLine();
        return this.promptPreferredDate();
    }    
    
    private int promptOderId()
    {
        System.out.print("Enter ORDER ID: ");
        return scanner.nextInt();
    }
    
    private int getSpecialCode()
    {
        int code;
        do {
            code = scanner.nextInt();
        } while (code < 0 || code > 1);
        return code;
    }
}
