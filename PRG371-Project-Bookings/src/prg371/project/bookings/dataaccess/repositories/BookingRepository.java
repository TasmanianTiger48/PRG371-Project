/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.dataaccess.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import prg371.project.bookings.business.enums.BookingStatusTypes;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.BookingModel;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;


/**
 *
 * @author User
 */
public class BookingRepository {
    
    public boolean addBooking(BookingModel booking) {
        String query = "INSERT INTO Bookings (EventTypeId, DecorateOptIn, EventDate, VenueAddress, " +
                    "Adults, Children, Status, CreatedDate, LastUpdateDate, UserId, CalculatedPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, booking.getEventTypeId());
            statement.setBoolean(2, booking.isDecorateOptIn());
            statement.setDate(3, Date.valueOf(booking.getEventDate()));
            statement.setString(4, booking.getVenueAddress());
            statement.setInt(5, booking.getAdultCount());
            statement.setInt(6, booking.getChildCount());
            statement.setInt(7, booking.getStatus().getKey());
            statement.setTimestamp(8, Timestamp.valueOf(booking.getCreatedDate()));
            statement.setTimestamp(9, Timestamp.valueOf(booking.getLastUpdateDate()));
            statement.setInt(10, booking.getUserId());
            statement.setDouble(11, booking.getCalculatedPrice());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBooking(BookingModel booking) {
        String query = "UPDATE Bookings SET EventTypeId = ?, DecorateOptIn = ?, EventDate = ?, " +
                    "VenueAddress = ?, Adults = ?, Children = ?, Status = ?, LastUpdateDate = ?, UserId = ?," +
                    " CalculatedPrice = ? WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, booking.getEventTypeId());
            statement.setBoolean(2, booking.isDecorateOptIn());
            statement.setDate(3, Date.valueOf(booking.getEventDate()));
            statement.setString(4, booking.getVenueAddress());
            statement.setInt(5, booking.getAdultCount());
            statement.setInt(6, booking.getChildCount());
            statement.setInt(7, booking.getStatus().getKey());
            statement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(9, booking.getUserId());
            statement.setDouble(10, booking.getCalculatedPrice());
            statement.setInt(11, booking.getId());
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeBooking(int bookingId) {
        String query = "UPDATE Bookings SET Status = 3 WHERE Id = ?";

        try (Connection connection = ConnectionProvider.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public BookingModel getBookingByDate(LocalDate eventDate) {
        String query = "SELECT * FROM Bookings WHERE EventDate = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(eventDate));
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return mapRowToBooking(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public List<BookingModel> getAllBookings() {
        return getAllBookingsByUser(null);
    }

    // Get all bookings for a specific user
    public List<BookingModel> getAllBookingsByUser(Integer userId) {
        List<BookingModel> bookings = new ArrayList<>();
        String query = "SELECT b.Id AS BookingId, b.EventTypeId, b.DecorateOptIn, b.EventDate, b.VenueAddress, " +
                       "b.Adults, b.Children, b.Status, b.CreatedDate, b.LastUpdateDate, b.UserId, b.CalculatedPrice, " +
                       "u.Id AS UserId, u.Name AS UserName, u.Email AS UserEmail, u.Type AS UserType, " +
                       "e.Id AS EventTypeId, e.Description AS EventTypeDescription, e.BaseAmount AS EventTypeBaseAmount " +
                       "FROM Bookings b " +
                       "JOIN Users u ON b.UserId = u.Id " +
                       "JOIN EventTypes e ON b.EventTypeId = e.Id";
        if (userId != null) {
            query += " WHERE UserId = ?";
        }
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            if (userId != null) {
                statement.setInt(1, userId);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookingModel booking = mapRowToBookingIncludeEventTypeIncludeUser(resultSet);
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bookings;
    }

    // Map result set to BookingModel object
    private BookingModel mapRowToBooking(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        int eventTypeId = resultSet.getInt("EventTypeId");
        boolean decorateOptIn = resultSet.getBoolean("DecorateOptIn");
        Date eventDate = resultSet.getDate("EventDate");
        String venueAddress = resultSet.getString("VenueAddress");
        int adultCount = resultSet.getInt("Adults");
        int childCount = resultSet.getInt("Children");
        int status = resultSet.getInt("Status");
        Timestamp createdDate = resultSet.getTimestamp("CreatedDate");
        Timestamp lastUpdateDate = resultSet.getTimestamp("LastUpdateDate");
        int userId = resultSet.getInt("UserId");
        double calculatedPrice = resultSet.getInt("CalculatedPrice");

        return new BookingModel(id, eventTypeId, decorateOptIn, eventDate.toLocalDate(),
                                venueAddress, adultCount, childCount, BookingStatusTypes.fromKey(status),
                                createdDate.toLocalDateTime(), lastUpdateDate.toLocalDateTime(), userId,
                                calculatedPrice);
    }
    
    private BookingModel mapRowToBookingIncludeEventTypeIncludeUser(ResultSet resultSet) throws SQLException {
        int bookingId = resultSet.getInt("BookingId");
        boolean decorateOptIn = resultSet.getBoolean("DecorateOptIn");
        LocalDate eventDate = resultSet.getDate("EventDate").toLocalDate();
        String venueAddress = resultSet.getString("VenueAddress");
        int adultCount = resultSet.getInt("Adults");
        int childCount = resultSet.getInt("Children");
        int status = resultSet.getInt("Status");
        LocalDateTime createdDate = resultSet.getTimestamp("CreatedDate").toLocalDateTime();
        LocalDateTime lastUpdateDate = resultSet.getTimestamp("LastUpdateDate").toLocalDateTime();
        double calculatedPrice = resultSet.getDouble("CalculatedPrice");
        
        int userId = resultSet.getInt("UserId");
        String userName = resultSet.getString("UserName");
        String userEmail = resultSet.getString("UserEmail");
        
        UserModel user = new UserModel();
        user.setUserId(userId);
        user.setName(userName);
        user.setEmail(userEmail);
        
        int eventTypeId = resultSet.getInt("EventTypeId");
        String eventTypeDescription = resultSet.getString("EventTypeDescription");
        double eventTypeBaseAmount = resultSet.getDouble("EventTypeBaseAmount");
        
        EventTypeModel eventType = new EventTypeModel();
        eventType.setId(eventTypeId);
        eventType.setDescription(eventTypeDescription);
        eventType.setBaseAmount(eventTypeBaseAmount);
        
        return new BookingModel(bookingId, eventTypeId, eventType, decorateOptIn, eventDate,
                                venueAddress, adultCount, childCount, BookingStatusTypes.fromKey(status),
                                createdDate, lastUpdateDate, userId, user,
                                calculatedPrice);
    }
}
