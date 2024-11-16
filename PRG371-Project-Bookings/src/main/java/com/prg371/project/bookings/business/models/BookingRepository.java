package com.prg371.project.bookings.business.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author RGottsche
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {

    public void createBooking(Booking booking) throws SQLException {
        String sql = "INSERT INTO Bookings (BookingID, ClientID, EventType, EventDate, VenueAddress, TotalPeople, KidsCount, AdultsCount, Menu, Decorations, IsConfirmed, BookingNumber) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, booking.getBookingID());
            pstmt.setString(2, booking.getClientID());
            pstmt.setString(3, booking.getEventType());
            pstmt.setDate(4, new java.sql.Date(booking.getEventDate().getTime()));
            pstmt.setString(5, booking.getVenueAddress());
            pstmt.setInt(6, booking.getTotalPeople());
            pstmt.setInt(7, booking.getKidsCount());
            pstmt.setInt(8, booking.getAdultsCount());
            pstmt.setString(9, booking.getMenu().toString());
            pstmt.setBoolean(10, booking.isDecorations());
            pstmt.setBoolean(11, booking.isConfirmed());
            pstmt.setString(12, booking.getBookingNumber());
            pstmt.executeUpdate();
        }
    }

    // Similar methods for getBooking, getAllBookings, updateBooking, and deleteBooking
}

