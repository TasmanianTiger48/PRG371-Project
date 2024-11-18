/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import prg371.project.bookings.business.enums.MenuItemCategoryTypes;

/**
 *
 * @author User
 */
public class MenuItemModel {
    private int id;
    private String name;
    private String description;
    private MenuItemCategoryTypes categoryType;
    private Double price;
    private Boolean isActive;

    public MenuItemModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }

    public MenuItemCategoryTypes getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(MenuItemCategoryTypes categoryType) {
        this.categoryType = categoryType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
    
    public MenuItemModel(String Name, String Description, MenuItemCategoryTypes categoryType, Double price) {
        this.name = Name;
        this.description = Description;
        this.categoryType = categoryType;
        this.price = price;
        this.isActive = true;
    }

    public MenuItemModel(int id, String Name, String Description, MenuItemCategoryTypes categoryType, Double price, Boolean isActive) {
        this.id = id;
        this.name = Name;
        this.description = Description;
        this.categoryType = categoryType;
        this.price = price;
        this.isActive = isActive;
    }
    
    public String getDisplayText() {
        return this.categoryType.getDescription()+ " - " + this.name;
    }
    
    public String validate() {
        if (this.name == null
            || this.name.isEmpty()
            || this.description == null
            || this.description.isEmpty()
            || this.price == null
        ) {
            return "Please enter values for all fields";
        }
        
        if (this.price <= 0) {
            return "Please enter a price greater than 0";
        }
        return null;
    }
    
}
