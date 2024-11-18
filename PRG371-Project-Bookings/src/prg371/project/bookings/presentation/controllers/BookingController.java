/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.presentation.controllers;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import prg371.project.bookings.Main;
import prg371.project.bookings.business.enums.BookingStatusTypes;
import prg371.project.bookings.business.models.BookingModel;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.business.services.BookingService;
import prg371.project.bookings.presentation.utilities.FrameUtils;

/**
 *
 * @author User
 */
public class BookingController {
    
    private final BookingService bookingService;
    private List<BookingModel> bookings;
    private BookingModel currentBooking;
    private Boolean isMenuItemsChanged = false;

    public void setIsMenuItemsChanged(Boolean isMenuItemsChanged) {
        this.isMenuItemsChanged = isMenuItemsChanged;
    }
    
    public Boolean IsMenuItemsChanged() {
        return this.isMenuItemsChanged;
    }
    
    public BookingController() {
        bookingService = new BookingService();
    }
    
    public BookingModel getBookingById(Integer bookingId) {
        for (BookingModel booking : bookings) {
            if (bookingId.equals(booking.getId())) {
                currentBooking = booking;
                return currentBooking;
            }
        }
        currentBooking = bookingService.getBookingById(bookingId);
        return currentBooking;
    }
    
    public List<BookingModel> loadBookings() {
        bookings = bookingService.getBookings();
        return bookings;
    }
    
    public Boolean addBooking(int eventTypeId, boolean decorateOptIn, Date eventDate, 
        String venueAddress, String adultCountString, String childCountString, Map<MenuItemModel, Integer> menuItems
    ) {
        try {
            return addBooking(eventTypeId, decorateOptIn, 
                FrameUtils.convertToLocalDate(eventDate), venueAddress,
                Integer.parseInt(adultCountString), Integer.parseInt(childCountString),
                menuItems);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for adults and children");
            return false;
        }
    }
    
    public Boolean addBooking(int eventTypeId, boolean decorateOptIn,
        LocalDate eventDate, String venueAddress, int adults, int children,
        Map<MenuItemModel, Integer> menuItems
    ) {
        BookingModel booking = new BookingModel(eventTypeId, decorateOptIn,
            eventDate, venueAddress, adults, children,
            Main.currentUser.getUserId(), menuItems);

        String message = booking.validate();

        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        if (bookingService.getBookingByDate(booking.getEventDate()) == null) {
            if (bookingService.addBooking(booking)) {
                JOptionPane.showMessageDialog(null, "Booking created successfully");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create booking");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Already another booking on that date");
            return false;
        }
    }
    
    public Boolean updateBooking(int id, int eventTypeId, boolean decorateOptIn, Date eventDate, 
        String venueAddress, String adultCountString, String childCountString, Map<MenuItemModel, Integer> menuItems
    ) {
        try {
            return updateBooking(id, eventTypeId, decorateOptIn, 
                FrameUtils.convertToLocalDate(eventDate), venueAddress,
                Integer.parseInt(adultCountString), Integer.parseInt(childCountString),
                menuItems);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Please enter valid numeric values for adults and children");
            return false;
        }
    }

    public Boolean updateBooking(int id, int eventTypeId, boolean decorateOptIn,
        LocalDate eventDate, String venueAddress, int adults, int children,
        Map<MenuItemModel, Integer> menuItems
    ) {
        BookingModel booking = new BookingModel(eventTypeId, decorateOptIn,
            eventDate, venueAddress, adults, children,
            Main.currentUser.getUserId(), menuItems);
        booking.setId(id);
        
        String message = booking.validate();

        if (message != null) {
            JOptionPane.showMessageDialog(null, message);
            return false;
        }
        BookingModel existingBooking = bookingService.getBookingByDate(booking.getEventDate());
        if (existingBooking == null || existingBooking.getId() == id) {
            if (bookingService.updateBooking(booking)) {
                JOptionPane.showMessageDialog(null, "Booking created successfully");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Failed to create booking");
                return false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Already another booking on that date");
            return false;
        }
    }
    
    public Boolean deleteBooking(int id) {
        if (bookingService.removeBooking(id)) {
            JOptionPane.showMessageDialog(null, "Booking cancelled");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to cancel Booking");
            return false;
        }
    }
    
    public Boolean confirmBooking(int id) {
        if (bookingService.confirmBooking(id)) {
            JOptionPane.showMessageDialog(null, "Booking confirmed");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Failed to confirm Booking");
            return false;
        }
    }
}
