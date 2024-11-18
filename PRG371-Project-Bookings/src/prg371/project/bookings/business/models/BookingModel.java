/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import prg371.project.bookings.business.enums.BookingStatusTypes;
import prg371.project.bookings.business.enums.MenuItemCategoryTypes;

/**
 *
 * @author User
 */
public class BookingModel {
    private int id;
    private int eventTypeId;
    private EventTypeModel eventType;
    private boolean decorateOptIn;
    private LocalDate eventDate;
    private String venueAddress;
    private int adultCount;
    private int childCount;
    private BookingStatusTypes status = BookingStatusTypes.Pending;
    private LocalDateTime createdDate;
    private LocalDateTime lastUpdateDate;
    private int userId;
    private UserModel user;
    private double calculatedPrice;
    private Map<MenuItemModel, Integer> linkedMenuItems;

    public BookingModel() {
    }

    public BookingModel(int id, int eventTypeId, EventTypeModel eventType, boolean decorateOptIn, LocalDate eventDate, String venueAddress, int adultCount, int childrenCount, BookingStatusTypes status, LocalDateTime createdDate, LocalDateTime lastUpdateDate, int userId, UserModel user, double calculatedPrice, Map<MenuItemModel, Integer> linkedMenuItems) {
        this.id = id;
        this.eventTypeId = eventTypeId;
        this.eventType = eventType;
        this.decorateOptIn = decorateOptIn;
        this.eventDate = eventDate;
        this.venueAddress = venueAddress;
        this.adultCount = adultCount;
        this.childCount = childrenCount;
        this.status = status;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.userId = userId;
        this.user = user;
        this.calculatedPrice = calculatedPrice;
        this.linkedMenuItems = linkedMenuItems;
        calculatePrice();
    }
    
    public BookingModel(int id, int eventTypeId, boolean decorateOptIn, LocalDate eventDate, String venueAddress, int adultCount, int childrenCount, BookingStatusTypes status, LocalDateTime createdDate, LocalDateTime lastUpdateDate, int userId, double calculatedPrice, Map<MenuItemModel, Integer> linkedMenuItems) {
        this.id = id;
        this.eventTypeId = eventTypeId;
        this.decorateOptIn = decorateOptIn;
        this.eventDate = eventDate;
        this.venueAddress = venueAddress;
        this.adultCount = adultCount;
        this.childCount = childrenCount;
        this.status = status;
        this.createdDate = createdDate;
        this.lastUpdateDate = lastUpdateDate;
        this.userId = userId;
        this.calculatedPrice = calculatedPrice;
        this.linkedMenuItems = linkedMenuItems;
        calculatePrice();
    }
    
    public BookingModel(int eventTypeId, boolean decorateOptIn, LocalDate eventDate, String venueAddress, int adultCount, int childrenCount, int userId, Map<MenuItemModel, Integer> linkedMenuItems) {
        this.eventTypeId = eventTypeId;
        this.decorateOptIn = decorateOptIn;
        this.eventDate = eventDate;
        this.venueAddress = venueAddress;
        this.adultCount = adultCount;
        this.childCount = childrenCount;
        this.userId = userId;
        this.linkedMenuItems = linkedMenuItems;
        calculatePrice();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public EventTypeModel getEventType() {
        return eventType;
    }

    public void setEventType(EventTypeModel eventType) {
        this.eventType = eventType;
        calculatePrice();
    }

    public boolean isDecorateOptIn() {
        return decorateOptIn;
    }

    public void setDecorateOptIn(boolean decorateOptIn) {
        this.decorateOptIn = decorateOptIn;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getVenueAddress() {
        return venueAddress;
    }

    public void setVenueAddress(String venueAddress) {
        this.venueAddress = venueAddress;
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childrenCount) {
        this.childCount = childrenCount;
    }

    public int getTotalPeopleCount() {
        return childCount + adultCount;
    }
    
    public BookingStatusTypes getStatus() {
        return status;
    }

    public void setStatus(BookingStatusTypes status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(LocalDateTime lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public double getCalculatedPrice() {
        return calculatedPrice;
    }

    public void setCalculatedPrice(double calculatedPrice) {
        this.calculatedPrice = calculatedPrice;
    }

    public Map<MenuItemModel, Integer> getLinkedMenuItems() {
        return linkedMenuItems;
    }

    public void setLinkedMenuItems(Map<MenuItemModel, Integer> linkedMenuItems) {
        this.linkedMenuItems = linkedMenuItems;
        calculatePrice();
    }
    
    public void calculatePrice() {
        double price = 0;
        
        if (linkedMenuItems != null) {
            for(MenuItemModel menuItem : linkedMenuItems.keySet()) {
                price += calculateMenuItemPrice(menuItem);
            }
        }
        
        if (this.eventType != null) {
            price += this.eventType.getBaseAmount();
        }
        
        this.calculatedPrice = price;
    }
    
    public String validate() {
        if (eventTypeId == 0) {
            return "Please select a Event Type";
        }
        
        if (eventDate == null) {
            return "Please select a Event Date";
        }
        
        if (venueAddress == null || venueAddress.isEmpty()) {
            return "Please select a Venue Address";
        }
        
        if (this.adultCount < 0) {
            return "Please enter a valid amount for adults";
        }
        
        if (this.childCount < 0) {
            return "Please enter a valid amount for children";
        }
        
        if (getTotalPeopleCount() < 1) {
            return "Please add people to the event";
        }
        
        if (this.linkedMenuItems == null || this.linkedMenuItems.size() < 1) {
            return "Please add menu items to the event";
        }
        
        return null;
    }
    
    public double calculateMenuItemPrice(MenuItemModel menuItem) {
        if (linkedMenuItems.containsKey(menuItem)) {
            int amount = linkedMenuItems.get(menuItem);
            double result = menuItem.getPrice() * amount;
            
            if (getTotalPeopleCount() > 50 && menuItem.getCategoryType() == MenuItemCategoryTypes.AdultMeal) {
                result = result - (result * 0.15);
            }
            
            return result;
        } else {
            return 0;
        }
    }
    
}
