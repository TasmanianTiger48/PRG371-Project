package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingManager implements IBookingManager {
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public Booking createBooking(String bookingID, String eventType, Date eventDate, String venueAddress, 
                                 int totalPeople, int kidsCount, int adultsCount, List<MenuItem> menu, boolean decorations) {
        Booking booking = new Booking(bookingID, eventType, eventDate, venueAddress, totalPeople, kidsCount, adultsCount, menu, decorations);
        bookings.add(booking);
        return booking;
    }

    @Override
    public void confirmBooking(Booking booking) {
        booking.setConfirmed(true);
        booking.setBookingNumber("BOOK" + booking.getBookingID());
    }

    @Override
    public void updateBooking(Booking booking, String newEventType, Date newEventDate, String newVenueAddress, 
                              int newTotalPeople, int newKidsCount, int newAdultsCount, List<MenuItem> newMenu, boolean newDecorations) {
        booking.setEventType(newEventType);
        booking.setEventDate(newEventDate);
        booking.setVenueAddress(newVenueAddress);
        booking.setTotalPeople(newTotalPeople);
        booking.setKidsCount(newKidsCount);
        booking.setAdultsCount(newAdultsCount);
        booking.setMenu(newMenu);
        booking.setDecorations(newDecorations);
    }

    @Override
    public String getBookingStatus(String bookingID) {
        return bookings.stream()
                .filter(b -> b.getBookingID().equals(bookingID))
                .map(b -> b.isConfirmed() ? "Confirmed" : "Pending")
                .findFirst()
                .orElse("Booking Not Found");
    }
}



