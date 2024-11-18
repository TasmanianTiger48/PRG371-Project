/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.presentation.controllers;

import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.business.services.EventTypeService;

/**
 *
 * @author User
 */
public class EventTypeController {
    private final EventTypeService eventTypeService;
    private List<EventTypeModel> eventTypes;
    
    public EventTypeController() {
        eventTypeService = new EventTypeService();
    }
    
    public EventTypeModel getEventTypeByDescription(String description) {
        for (EventTypeModel eventType : eventTypes) {
            if (description.equals(eventType.getDescription())) {
                return eventType;
            }
        }
        return null;
    }
    
    public List<EventTypeModel> loadEventTypes() {
        eventTypes = eventTypeService.getEventTypes();
        return eventTypes;
    }
    
    public Boolean addEventType(String desc, String amountString) {
        try {
            return addEventType(desc, Double.parseDouble(amountString));
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
            return false;
        }
    }
    
    public Boolean addEventType(String desc, Double amount) {
        EventTypeModel event = new EventTypeModel(desc, amount);
        String message = event.validate();
        
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        
        
        if (eventTypeService.addEventType(event)) {
            JOptionPane.showMessageDialog(null, "Event Type added successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add Event Type, it may already exist");
            return false;
        }
    }
    
    public Boolean updateEventType(int id, String desc, String amountString) {
        try {
            return updateEventType(id, desc, Double.parseDouble(amountString));
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter a valid numeric amount");
            return false;
        }
    }
    
    public Boolean updateEventType(int id, String desc, Double amount) {
        EventTypeModel event = new EventTypeModel(id, desc, amount, true);
        String message = event.validate();
        
        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        
        if (eventTypeService.updateEventType(event)) {
            JOptionPane.showMessageDialog(null, "Event Type updated successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update Event Type");
            return false;
        }
    }
    
    public Boolean deleteEventType(int id) {
        if (eventTypeService.removeEventType(id)) {
            JOptionPane.showMessageDialog(null, "Event Type removed successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to remove Event Type");
            return false;
        }
    }
    
    public Boolean addLinkedMenuItem(int eventTypeId, int menuItemId) {
        if (eventTypeId < 1) {
            JOptionPane.showMessageDialog(null, "Please select a value for Event Type");
            return false;
        }
        
        if (menuItemId < 1) {
            JOptionPane.showMessageDialog(null, "Please select a value for Menu Item");
            return false;
        }
        
        if (eventTypeService.addLinkedMenuItem(eventTypeId, menuItemId)) {
            JOptionPane.showMessageDialog(null, "Linked Menu Item added successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add Linked Menu Item, it may already exist");
            return false;
        }
    }
    
    public Map<EventTypeModel, List<MenuItemModel>> loadEventTypesLinkedMenuItems() {
        return eventTypeService.getEventTypeMenuItems();
    }
    
    public Boolean deleteLinkedMenuItem(int eventTypeId, int menuItemId) {
        if (eventTypeService.removeLinkedMenuItem(eventTypeId, menuItemId)) {
            JOptionPane.showMessageDialog(null, "Linked Menu Item removed successfully");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to remove Linked Menu Item");
            return false;
        }
    }
}
