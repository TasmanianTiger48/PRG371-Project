package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.util.Date;
import java.util.List;

public interface IBookingManager {
    Booking createBooking(String bookingID, String eventType, Date eventDate, String venueAddress, 
                          int totalPeople, int kidsCount, int adultsCount, List<MenuItem> menu, boolean decorations);

    void confirmBooking(Booking booking);

    void updateBooking(Booking booking, String newEventType, Date newEventDate, String newVenueAddress, 
                       int newTotalPeople, int newKidsCount, int newAdultsCount, List<MenuItem> newMenu, boolean newDecorations);

    String getBookingStatus(String bookingID);
}

