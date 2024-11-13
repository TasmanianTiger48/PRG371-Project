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

public class NotificationRepository {

    public void createNotification(Notification notification) throws SQLException {
        String sql = "INSERT INTO Notifications (NotificationID, ClientID, Message) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, notification.getNotificationID());
            pstmt.setString(2, notification.getClientID());
            pstmt.setString(3, notification.getMessage());
            pstmt.executeUpdate();
        }
    }

    // Similar methods for getNotification, getAllNotifications, updateNotification, and deleteNotification
}

