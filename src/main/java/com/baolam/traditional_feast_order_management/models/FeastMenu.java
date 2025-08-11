/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.baolam.traditional_feast_order_management.models;

/**
 *
 * @author lam01
 */
public class FeastMenu implements Comparable<FeastMenu> {
    private String menuCode;
    private String name;
    private int price;
    private String starter;
    private String mainCourse;
    private String dessert;
    
    public FeastMenu() {
    }    

    public FeastMenu(String menuCode, String name, int price, String starter, String mainCourse, String dessert) {
        this.menuCode = menuCode;
        this.name = name;
        this.price = price;
        this.starter = starter;
        this.mainCourse = mainCourse;
        this.dessert = dessert;
    }
    
    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public String getMainCourse() {
        return mainCourse;
    }

    public void setMainCourse(String mainCourse) {
        this.mainCourse = mainCourse;
    }

    public String getDessert() {
        return dessert;
    }

    public void setDessert(String dessert) {
        this.dessert = dessert;
    }
    
    @Override
    public int compareTo(FeastMenu o) {
        return this.price - o.getPrice();
    }
}
