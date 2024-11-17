/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import prg371.project.bookings.business.enums.BookingStatusTypes;

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
    private BookingStatusTypes status;
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
    }
    
    public String validate() {
        return null;
    }
    
}
