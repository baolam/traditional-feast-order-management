/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.views;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.a7.A7_Grids;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.util.Scanner;

/**
 *
 * @author lam01
 */
public class MainMenu {
    private Scanner scanner = null;
    private AsciiTable defaultMenu = null;
    
    public enum Menu {
      CODE_FAILED,
      REGISTER_CUSTOMER, 
      UPDATE_CUSTOMER,
      SEARCH_CUSTOMER_BY_NAME,
      DISPLAY_FEAST,
      PLACE_FEAST_ORDER,
      UPDATE_ORDER,
      SAVE_DATA,
      DISPLAY_CHOICE,
      EXIT_PROGRAM
    };
    
    public MainMenu() {
        scanner = new Scanner(System.in);
        buildShowOverall();
    }
        
    private void buildShowOverall()
    {
        defaultMenu = new AsciiTable();
        defaultMenu.getContext().setGrid(A7_Grids.minusBarPlusEquals());
        
        defaultMenu.addRule();
        defaultMenu.addRow("MAIN MENU").setTextAlignment(TextAlignment.CENTER);
        defaultMenu.addRule();
        defaultMenu.addRow("1. Register Customer");
        defaultMenu.addRow("2. Update customer information");
        defaultMenu.addRow("3. Search for customer information by name");
        defaultMenu.addRow("4. Display feast menus");
        defaultMenu.addRow("5. Place a feast order");
        defaultMenu.addRow("6. Update order information");
        defaultMenu.addRow("7. Save data to file");
        defaultMenu.addRow("8. Display Customer or Order lists");
        defaultMenu.addRow("9. Exit");
        defaultMenu.addRule();
    }
    
    public void showOveallPrompt()
    {
        String mainMenu = defaultMenu.render();
        System.out.println(mainMenu);
    }
    
    public Menu prompt()
    {
        System.out.print("Enter code: ");
        int code = scanner.nextInt();
        if (code < 1 || code > 9)
        {
            System.out.println("Wrong code!");
            return Menu.CODE_FAILED;
        }
        return Menu.values()[code];
    }
    
    /***
     * @exception 
     * Hỏi người dùng có lặp lại hành động đã làm trước đó hay không
     * @return 
     */
    public boolean shouldContinueDoing()
    {

        int code = scanner.nextInt();
        if (code < 0 || code > 1)
        {
            System.out.println("Wrong code!");
            return this.shouldContinueDoing();
        }
        return code == 1;
    }
    
    public boolean shouldContinueDoing(boolean skipFirstLine)
    {
        if (!skipFirstLine) System.out.print("Doing again(1/0)? ");
        return this.shouldContinueDoing();
    }   
}
