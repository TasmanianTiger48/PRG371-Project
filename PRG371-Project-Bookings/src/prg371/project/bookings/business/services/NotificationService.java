/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.services;

import java.util.List;
import prg371.project.bookings.Main;
import prg371.project.bookings.business.enums.NotificationTypes;
import prg371.project.bookings.business.enums.UserTypes;
import prg371.project.bookings.business.models.NotificationModel;
import prg371.project.bookings.dataaccess.repositories.NotificationRepository;

/**
 *
 * @author User
 */
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public NotificationService() {
        this.notificationRepository = new NotificationRepository();
    }

    public boolean addNotification(NotificationModel notification) {
        return notificationRepository.addNotification(notification);
    }

    public List<NotificationModel> getNotifications() {
        if (Main.currentUser.getType() == UserTypes.Admin) {
            return notificationRepository.getAllAdminNotifications();
        } else {
            return notificationRepository.getAllNotifications(Main.currentUser.getUserId());
        }
    }

    public boolean dismissNotification(int notificationId) {
        return notificationRepository.dismissNotification(notificationId);
    }
    
    public boolean createNotification(int bookingId, NotificationTypes type) {
        NotificationModel notification = new NotificationModel(bookingId, type.getMessage(), type);
        return notificationRepository.addNotification(notification);
    }
}
