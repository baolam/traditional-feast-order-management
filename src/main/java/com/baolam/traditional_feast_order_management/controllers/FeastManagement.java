/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.controllers;

import com.baolam.traditional_feast_order_management.models.FeastMenu;
import com.baolam.traditional_feast_order_management.models.rules.FeastMenuRule;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author lam01
 */
public class FeastManagement {
    private boolean _fileNotFound = false;
    private FeastMenuRule feastMenuRule = null;
    
    protected String contentFile = "feastMenu.csv";
    protected ArrayList<FeastMenu> feastMenus = new ArrayList();
    
    public FeastManagement() {
        this.loadDataFromFile();
    }
    
    public void setFeastMenuRule(FeastMenuRule feastMenuRule) {
        this.feastMenuRule = feastMenuRule;
    }

    public FeastMenuRule getFeastMenuRule() {
        return feastMenuRule;
    }

    public ArrayList<FeastMenu> getFeastMenus() {
        return feastMenus;
    }
    
    /***
     * @exception 
     * Load dữ liệu vào mảng quản lí thông qua đọc file
     */
    private void loadDataFromFile()
    {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(
               new FileInputStream(contentFile), StandardCharsets.UTF_8)
            );
            String[] line;
            while ((line = reader.readNext()) != null)
            {
                feastMenus.add(this.createMenuItemFromString(line));
            }
            
            /// Sau khi load xong thì sort lại
            Collections.sort(feastMenus);
        }  catch (IOException e)
        {
            _fileNotFound = true;
        } catch (CsvValidationException ex) {
            System.getLogger(FeastManagement.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    public boolean isFileNotFound() {
        return _fileNotFound;
    }
    
    /***
     * 
     * @param line
     * @exception 
     * Hỗ trợ việc tạo Object từ dòng dữ liệu
     * @return 
     */
    private FeastMenu createMenuItemFromString(String[]line)
    {
        FeastMenu menuItem = new FeastMenu();
        /// Định dạng dữ liệu, 0, 1, 2, 3
        menuItem.setMenuCode(line[0]);
        menuItem.setName(line[1]);
        menuItem.setPrice(Integer.parseInt(line[2]));
        menuItem.setStarter(line[3]);
        menuItem.setMainCourse(line[4]);
        menuItem.setDessert(line[5]);
        
        return menuItem;
    }
    
    /***
     * 
     * @param code
     * @exception 
     * Tìm kiếm menu bằng code được cung cấp
     * @return 
     */
    public FeastMenu retrieveMenuByCode(String code)
    {
        FeastMenu menuItem = null;
        if (! feastMenuRule.checkFeastCode(code))
            return menuItem;
        
        for (FeastMenu item : feastMenus)
        {
            if (item.getMenuCode().equals(code))
            {
                menuItem = item;
                break;
            }
        }
        
        return menuItem;
    }
}
