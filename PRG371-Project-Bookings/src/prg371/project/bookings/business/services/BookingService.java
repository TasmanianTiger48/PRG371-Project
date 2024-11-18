/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.services;

import java.util.List;
import prg371.project.bookings.Main;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.BookingModel;
import prg371.project.bookings.dataaccess.repositories.BookingRepository;

/**
 *
 * @author User
 */
public class BookingService {
    private final BookingRepository bookingRepository;
    
    public BookingService() {
        this.bookingRepository = new BookingRepository();
    }
        
    public boolean addBooking(BookingModel booking) {
        BookingModel existingBooking = bookingRepository.getBookingByDate(booking.getEventDate());
        if (existingBooking != null) {
            return false;
        }
        return bookingRepository.addBooking(booking);
    }
    
    public boolean updateBooking(BookingModel booking) {
        return bookingRepository.updateBooking(booking);
    }
    
    public boolean removeBooking(int id) {
        return bookingRepository.removeBooking(id);
    }
    
    public List<BookingModel> getBookings() {
        if (Main.currentUser.getType() == UserTypes.Admin) {
            return bookingRepository.getAllBookings();
        }
        return bookingRepository.getAllBookingsByUser(Main.currentUser.getUserId());
    }
    
    public BookingModel getBookingById(int bookingId) {
        return bookingRepository.getBookingById(bookingId);
    }
    
}
