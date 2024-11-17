/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.services;

import java.util.List;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.dataaccess.repositories.MenuItemRepository;

/**
 *
 * @author User
 */
public class MenuItemService {
    private final MenuItemRepository menuItemRepository;
    
    public MenuItemService() {
        this.menuItemRepository = new MenuItemRepository();
    }
        
    public boolean addMenuItem(MenuItemModel item) {
        MenuItemModel existingItem = menuItemRepository.getMenuItemByName(item.getName());
        if (existingItem != null) {
            if (!existingItem.getIsActive()) {
                item.setId(existingItem.getId());
                return menuItemRepository.updateMenuItem(item);
            }
            return false;
        }
        return menuItemRepository.addMenuItem(item);
    }
    
    public boolean updateMenuItem(MenuItemModel item) {
        return menuItemRepository.updateMenuItem(item);
    }
    
    public boolean removeMenuItem(int id) {
        return menuItemRepository.removeMenuItem(id);
    }
    
    public List<MenuItemModel> getMenuItems() {
        return menuItemRepository.getActiveMenuItems();
    }
}
