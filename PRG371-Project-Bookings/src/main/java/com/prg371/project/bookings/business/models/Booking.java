package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.util.Date;
import java.util.List;

public class Booking {
    private String bookingID;
    private String clientID;
    private String eventType;
    private Date eventDate;
    private String venueAddress;
    private int totalPeople;
    private int kidsCount;
    private int adultsCount;
    private List<MenuItem> menu;
    private boolean decorations;
    private boolean isConfirmed;
    private String bookingNumber;

    public Booking(String bookingID, String eventType, Date eventDate, String venueAddress, int totalPeople,
                   int kidsCount, int adultsCount, List<MenuItem> menu, boolean decorations) {
        this.bookingID = bookingID;
        this.eventType = eventType;
        this.eventDate = eventDate;
        this.venueAddress = venueAddress;
        this.totalPeople = totalPeople;
        this.kidsCount = kidsCount;
        this.adultsCount = adultsCount;
        this.menu = menu;
        this.decorations = decorations;
        this.isConfirmed = false; // Default to false until confirmed
    }

    // Getters and Setters
    public String getBookingID() { return bookingID; }
    public void setBookingID(String bookingID) { this.bookingID = bookingID; }
    
    public String getClientID() { return clientID; }
    public void setClientID(String clientID) { this.clientID = clientID; }
    
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Date getEventDate() { return eventDate; }
    public void setEventDate(Date eventDate) { this.eventDate = eventDate; }

    public String getVenueAddress() { return venueAddress; }
    public void setVenueAddress(String venueAddress) { this.venueAddress = venueAddress; }

    public int getTotalPeople() { return totalPeople; }
    public void setTotalPeople(int totalPeople) { this.totalPeople = totalPeople; }

    public int getKidsCount() { return kidsCount; }
    public void setKidsCount(int kidsCount) { this.kidsCount = kidsCount; }

    public int getAdultsCount() { return adultsCount; }
    public void setAdultsCount(int adultsCount) { this.adultsCount = adultsCount; }

    public List<MenuItem> getMenu() { return menu; }
    public void setMenu(List<MenuItem> menu) { this.menu = menu; }

    public boolean isDecorations() { return decorations; }
    public void setDecorations(boolean decorations) { this.decorations = decorations; }

    public boolean isConfirmed() { return isConfirmed; }
    public void setConfirmed(boolean confirmed) { isConfirmed = confirmed; }

    public String getBookingNumber() { return bookingNumber; }
    public void setBookingNumber(String bookingNumber) { this.bookingNumber = bookingNumber; }
}
