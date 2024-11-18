/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.presentation.controllers;

import java.util.List;
import javax.swing.JOptionPane;
import prg371.project.bookings.business.enums.MenuItemCategoryTypes;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.business.services.MenuItemService;

/**
 *
 * @author User
 */
public class MenuItemController {
    private final MenuItemService menuItemService;
    
    public MenuItemController() {
        menuItemService = new MenuItemService();
    }
    
    public List<MenuItemModel> loadMenuItems() {
        return menuItemService.getMenuItems();
    }
    
    public List<MenuItemModel> loadMenuItemsByEventType(int eventTypeId) {
        return menuItemService.getMenuItemsByEventType(eventTypeId);
    }
    
    public Boolean addMenuItem(String name, String desc, int categoryType, String priceString) {
        try {
            addMenuItem(name, desc, MenuItemCategoryTypes.fromKey(categoryType), Double.parseDouble(priceString));
            return true;
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric price");
            return false;
        }
        catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(null, "Please select a valid category type");
            return false;
        }
    }
    
    public Boolean addMenuItem(String name, String desc, MenuItemCategoryTypes categoryType, Double price) {
        MenuItemModel item = new MenuItemModel(name, desc, categoryType, price);
        String message = item.validate();
        
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        
        
        if (menuItemService.addMenuItem(item)) {
            JOptionPane.showMessageDialog(null, "Menu Item added successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add Menu Item, it may already exist");
            return false;
        }
    }
    
    public Boolean updateMenuItem(int id, String name, String desc, int categoryType, String priceString) {
        try {
            return updateMenuItem(id, name, desc, MenuItemCategoryTypes.fromKey(categoryType), Double.parseDouble(priceString));
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric price");
            return false;
        }
    }
    
    public Boolean updateMenuItem(int id, String name, String desc, MenuItemCategoryTypes categoryType, Double price) {
        MenuItemModel item = new MenuItemModel(id, name, desc, categoryType, price, true);
        String message = item.validate();
        
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        
        if (menuItemService.updateMenuItem(item)) {
            JOptionPane.showMessageDialog(null, "Menu Item updated successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update Menu Item");
            return false;
        }
    }
    
    public Boolean deleteMenuType(int id) {
        if (menuItemService.removeMenuItem(id)) {
            JOptionPane.showMessageDialog(null, "Menu Item removed successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to remove Menu Item");
            return false;
        }
    }
}
