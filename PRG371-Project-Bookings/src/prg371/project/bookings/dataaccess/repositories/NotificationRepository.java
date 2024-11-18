/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.dataaccess.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import prg371.project.bookings.business.enums.NotificationTypes;
import prg371.project.bookings.business.models.NotificationModel;
import prg371.project.bookings.dataaccess.ConnectionProvider;

/**
 *
 * @author User
 */
public class NotificationRepository {
    public boolean addNotification(NotificationModel notification) {
        String query = "INSERT INTO Notifications (BookingId, Message, NotificationType, Dismissed) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = ConnectionProvider.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, notification.getBookingId());
            statement.setString(2, notification.getMessage());
            statement.setInt(3, notification.getNotificationType().getKey());
            statement.setBoolean(4, notification.isDismissed());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Method to get all notifications
    public List<NotificationModel> getAllNotifications(int userId) {
        List<NotificationModel> notifications = new ArrayList<>();
        String query = "SELECT n.* FROM Notifications n INNER JOIN Bookings b ON n.BookingId = b.Id WHERE b.UserId ? AND n.Dismissed = FALSE";
        
        try (Connection connection = ConnectionProvider.getConnection(); 
            PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setInt(1, userId);
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                NotificationModel notification = mapRowToNotification(resultSet);
                notifications.add(notification);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return notifications;
    }
    
    public List<NotificationModel> getAllAdminNotifications() {
        List<NotificationModel> notifications = new ArrayList<>();
        String query = "SELECT * FROM Notifications WHERE NotificationType IN (1, 3, 4) AND Dismissed = FALSE";
        
        try (Connection connection = ConnectionProvider.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                NotificationModel notification = mapRowToNotification(resultSet);
                notifications.add(notification);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return notifications;
    }

    public boolean dismissNotification(int notificationId) {
        String query = "UPDATE Notifications SET Dismissed = ? WHERE Id = ?";
        
        try (Connection connection = ConnectionProvider.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setBoolean(1, true);
            statement.setInt(2, notificationId);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    // Method to map a ResultSet to a NotificationModel object
    private NotificationModel mapRowToNotification(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("Id");
        int bookingId = resultSet.getInt("BookingId");
        String message = resultSet.getString("Message");
        int notificationTypeKey = resultSet.getInt("NotificationType");
        boolean dismissed = resultSet.getBoolean("Dismissed");
        LocalDateTime createdDate = resultSet.getTimestamp("DateCreated").toLocalDateTime();
        NotificationTypes notificationType = NotificationTypes.fromKey(notificationTypeKey);
        
        return new NotificationModel(id, bookingId, message, createdDate, notificationType, dismissed);
    }
}
