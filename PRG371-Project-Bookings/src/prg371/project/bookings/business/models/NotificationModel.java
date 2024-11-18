/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prg371.project.bookings.business.models;

import java.time.LocalDateTime;
import prg371.project.bookings.business.enums.NotificationTypes;

/**
 *
 * @author User
 */
public class NotificationModel {
    private int id;
    private int bookingId;
    private String message;
    private LocalDateTime dateCreated;
    private NotificationTypes notificationType;
    private boolean dismissed;

    public NotificationModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public NotificationTypes getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationTypes notificationType) {
        this.notificationType = notificationType;
    }

    public boolean isDismissed() {
        return dismissed;
    }

    public void setDismissed(boolean dismissed) {
        this.dismissed = dismissed;
    }

    public NotificationModel(int id, int bookingId, String message, LocalDateTime dateCreated, NotificationTypes notificationType, boolean dismissed) {
        this.id = id;
        this.bookingId = bookingId;
        this.message = message;
        this.dateCreated = dateCreated;
        this.notificationType = notificationType;
        this.dismissed = dismissed;
    }
    
    public NotificationModel(int bookingId, String message, NotificationTypes notificationType) {
        this.bookingId = bookingId;
        this.message = message;
        this.notificationType = notificationType;
        this.dismissed = false;
    }
}
