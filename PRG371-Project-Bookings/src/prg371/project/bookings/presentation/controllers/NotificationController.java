/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.presentation.controllers;

import java.util.List;
import javax.swing.JOptionPane;
import prg371.project.bookings.business.enums.NotificationTypes;
import prg371.project.bookings.business.models.NotificationModel;
import prg371.project.bookings.business.services.NotificationService;

/**
 *
 * @author User
 */
public class NotificationController {
    
    private final NotificationService notificationService;

    public NotificationController() {
        this.notificationService = new NotificationService();
    }

    public void handleCreateNotification(int bookingId, NotificationTypes notificationType) {
        try {
            if (!notificationService.createNotification(bookingId, notificationType)) {
                JOptionPane.showMessageDialog(null, "Failed to create Notification");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to create Notification");
        }
    }

    public List<NotificationModel> loadNotifications() {
        return notificationService.getNotifications();
    }

    public void handleDismissNotification(int notificationId) {
        try {
            if (!notificationService.dismissNotification(notificationId)) {
                JOptionPane.showMessageDialog(null, "Failed to dismiss notification");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to dismiss notification");
        }
    }
}
