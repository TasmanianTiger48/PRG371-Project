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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import prg371.project.bookings.business.enums.BookingStatusTypes;
import prg371.project.bookings.business.enums.MenuItemCategoryTypes;
import prg371.project.bookings.business.models.BookingModel;
import prg371.project.bookings.business.models.EventTypeModel;
import prg371.project.bookings.business.models.MenuItemModel;
import prg371.project.bookings.business.models.UserModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;


/**
 *
 * @author User
 */
public class BookingRepository {
    
    private final String queryAllBookingData = "SELECT b.Id AS BookingId, b.EventTypeId, b.DecorateOptIn, b.EventDate, b.VenueAddress, " +
                "b.Adults, b.Children, b.Status, b.CreatedDate, b.LastUpdateDate, b.UserId, b.CalculatedPrice, " +
                "u.Id AS UserId, u.Name AS UserName, u.Email AS UserEmail, u.Type AS UserType, " +
                "e.Id AS EventTypeId, e.Description AS EventTypeDescription, e.BaseAmount AS EventTypeBaseAmount, " +
                "m.Id AS MenuItemId, m.Name AS MenuItemName, m.Description AS MenuItemDescription, " +
                "m.Price AS MenuItemPrice, m.CategoryType AS MenuItemCategoryType, " +
                "bmi.Amount AS MenuItemAmount " +
            "FROM Bookings b " +
            "INNER JOIN Users u ON b.UserId = u.Id " +
            "INNER JOIN EventTypes e ON b.EventTypeId = e.Id " +
            "LEFT JOIN BookingMenuItems bmi ON b.Id = bmi.BookingId " +
            "LEFT JOIN MenuItems m ON bmi.MenuItemId = m.Id";
    
    public boolean addBooking(BookingModel booking) {
        String bookingQuery = "INSERT INTO Bookings (EventTypeId, DecorateOptIn, EventDate, VenueAddress, " +
                    "Adults, Children, Status, CreatedDate, LastUpdateDate, UserId, CalculatedPrice) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        String linkedMenuItemsQuery = "INSERT INTO BookingMenuItems (BookingId, MenuItemId, Amount) VALUES (?, ?, ?)";
        
        Connection connection = null;
        
        try {
            connection = ConnectionProvider.getConnection();
            connection.setAutoCommit(false);
            int bookingId = 0;
                    
            try (PreparedStatement bookingStatement = connection.prepareStatement(bookingQuery, Statement.RETURN_GENERATED_KEYS)) {
                bookingStatement.setInt(1, booking.getEventTypeId());
                bookingStatement.setBoolean(2, booking.isDecorateOptIn());
                bookingStatement.setDate(3, Date.valueOf(booking.getEventDate()));
                bookingStatement.setString(4, booking.getVenueAddress());
                bookingStatement.setInt(5, booking.getAdultCount());
                bookingStatement.setInt(6, booking.getChildCount());
                bookingStatement.setInt(7, booking.getStatus().getKey());
                bookingStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                bookingStatement.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
                bookingStatement.setInt(10, booking.getUserId());
                bookingStatement.setDouble(11, booking.getCalculatedPrice());

                int rowsAffected = bookingStatement.executeUpdate();
                if (rowsAffected == 0)
                    throw new SQLException("Failed to create Booking");

                try (ResultSet generatedKeys = bookingStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        bookingId = generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Failed to get booking id");
                    }
                }
            }
            
            try (PreparedStatement menuItemsStatement = connection.prepareStatement(linkedMenuItemsQuery)) {
                for ( Map.Entry<MenuItemModel, Integer> item : booking.getLinkedMenuItems().entrySet()) {
                    menuItemsStatement.setInt(1, bookingId);
                    menuItemsStatement.setInt(2, item.getKey().getId());
                    menuItemsStatement.setInt(3, item.getValue());
                    menuItemsStatement.addBatch();
                }
                menuItemsStatement.executeBatch();
            }
            
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public boolean updateBooking(BookingModel booking) {
        String bookingQuery = "UPDATE Bookings SET EventTypeId = ?, DecorateOptIn = ?, EventDate = ?, " +
                    "VenueAddress = ?, Adults = ?, Children = ?, Status = ?, LastUpdateDate = ?, UserId = ?," +
                    " CalculatedPrice = ? WHERE Id = ?";
        
        String deleteLinkedMenuItemsQuery = "DELETE FROM BookingMenuItems WHERE BookingId = ?";
        String insertLinkedMenuItemsQuery = "INSERT INTO BookingMenuItems (BookingId, MenuItemId, Amount) VALUES (?, ?, ?)";
        
        Connection connection = null;
        
        try {
            connection = ConnectionProvider.getConnection();
            connection.setAutoCommit(false);
                
            try (PreparedStatement bookingStatement = connection.prepareStatement(bookingQuery)) {
                bookingStatement.setInt(1, booking.getEventTypeId());
                bookingStatement.setBoolean(2, booking.isDecorateOptIn());
                bookingStatement.setDate(3, Date.valueOf(booking.getEventDate()));
                bookingStatement.setString(4, booking.getVenueAddress());
                bookingStatement.setInt(5, booking.getAdultCount());
                bookingStatement.setInt(6, booking.getChildCount());
                bookingStatement.setInt(7, booking.getStatus().getKey());
                bookingStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
                bookingStatement.setInt(9, booking.getUserId());
                bookingStatement.setDouble(10, booking.getCalculatedPrice());
                bookingStatement.setInt(11, booking.getId());
                
                int affectedRows = bookingStatement.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Failed to update Booking");
                }
            }

            try (PreparedStatement deleteMenuItemsStatement = connection.prepareStatement(deleteLinkedMenuItemsQuery)) {
                deleteMenuItemsStatement.setInt(1, booking.getId());
                deleteMenuItemsStatement.executeUpdate();
            }
            
            try (PreparedStatement insertLinkedMenuItemsStatement = connection.prepareStatement(insertLinkedMenuItemsQuery)) {
                for (Map.Entry<MenuItemModel, Integer> item : booking.getLinkedMenuItems().entrySet()) {
                    insertLinkedMenuItemsStatement.setInt(1, booking.getId());
                    insertLinkedMenuItemsStatement.setInt(2, item.getKey().getId());
                    insertLinkedMenuItemsStatement.setInt(3, item.getValue());
                    insertLinkedMenuItemsStatement.addBatch();
                }
                insertLinkedMenuItemsStatement.executeBatch();
            }
            
            connection.commit();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
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
        String query = "SELECT * FROM Bookings WHERE EventDate = ? AND NOT Status = ?";

        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setDate(1, Date.valueOf(eventDate));
            statement.setInt(2, BookingStatusTypes.Cancelled.getKey());
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
        Map<Integer, BookingModel> bookings = new HashMap<>();
        String query = queryAllBookingData;
        
        if (userId != null) {
            query += " WHERE b.UserId = ?";
        }
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            if (userId != null) {
                statement.setInt(1, userId);
            }
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                BookingModel booking = bookings.get(resultSet.getInt("BookingId"));
                
                if (booking == null) {
                    booking = mapRowToBookingIncludeEventTypeIncludeUser(resultSet);
                    bookings.put(booking.getId(), booking);
                }
                
                int menuItemId = resultSet.getInt("MenuItemId");
                if (menuItemId != 0) {
                    String menuItemName = resultSet.getString("MenuItemName");
                    String menuItemDescription = resultSet.getString("MenuItemDescription");
                    int menuItemAmount = resultSet.getInt("MenuItemAmount");
                    int menuItemCategoryType = resultSet.getInt("MenuItemCategoryType");
                    double menuItemPrice = resultSet.getDouble("MenuItemPrice");
                    
                    MenuItemModel menuItem = new MenuItemModel(menuItemId, menuItemName, menuItemDescription, MenuItemCategoryTypes.fromKey(menuItemCategoryType), menuItemPrice, true);
                    
                    booking.getLinkedMenuItems().put(menuItem, menuItemAmount);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(bookings.values());
    }
    
    public BookingModel getBookingById(int bookingId) {
        String query = queryAllBookingData;
        query += " WHERE b.Id = ?";
        
        try (Connection connection = ConnectionProvider.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                BookingModel booking = mapRowToBookingIncludeEventTypeIncludeUser(resultSet);
                int menuItemId = resultSet.getInt("MenuItemId");
                if (menuItemId != 0) {
                    String menuItemName = resultSet.getString("MenuItemName");
                    String menuItemDescription = resultSet.getString("MenuItemDescription");
                    int menuItemAmount = resultSet.getInt("MenuItemAmount");
                    int menuItemCategoryType = resultSet.getInt("MenuItemCategoryType");
                    double menuItemPrice = resultSet.getDouble("MenuItemPrice");
                    
                    MenuItemModel menuItem = new MenuItemModel(menuItemId, menuItemName, menuItemDescription, MenuItemCategoryTypes.fromKey(menuItemCategoryType), menuItemPrice, true);
                    
                    booking.getLinkedMenuItems().put(menuItem, menuItemAmount);
                }
                
                return booking;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

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
                                calculatedPrice, null);
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
                                calculatedPrice, new HashMap<>());
    }
}
